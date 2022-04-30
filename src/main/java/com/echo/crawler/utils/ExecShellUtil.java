package com.echo.crawler.utils;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 执行命令的工具类
 */
public class ExecShellUtil {
    private Session session;

    private Channel channel;

    private ChannelExec channelExec;

    private static final String DONOT_INIT_ERROR = "Please invoke init method!";

    public static ExecShellUtil getInstance() {
        return new ExecShellUtil();
    }

    public void init(String ip, Integer port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        jsch.getSession(username, ip, port);
        session = jsch.getSession(username, ip, port);
        session.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        int defaultConnectTimeout = 60 * 100;
        session.connect(defaultConnectTimeout);

        channel = session.openChannel("exec");
        channelExec = (ChannelExec) channel;
    }

    public String execCmd(String commonds) throws Exception {
        if (session == null || channel == null || channelExec == null) {
            throw new JSchException(DONOT_INIT_ERROR);
        }
        channelExec.setCommand(commonds);
        channel.setInputStream(null);
        channelExec.setErrStream(System.err);
        channel.connect();
        StringBuilder sb = new StringBuilder(16);
        try (InputStream in = channelExec.getInputStream();
             InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                sb.append("\n").append(buffer);
            }
            close();
            return sb.toString();
        }
    }

    public void close() {
        if (channelExec != null && channelExec.isConnected()) {
            channelExec.disconnect();
        }
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
