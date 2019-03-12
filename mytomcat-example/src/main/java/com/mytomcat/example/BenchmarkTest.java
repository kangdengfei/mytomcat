package com.mytomcat.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @program: jixianchannel-parent
 * @author: KDF
 * @create: 2019-03-12 14:25
 **/
@BenchmarkMode(Mode.Throughput)
public class BenchmarkTest {

    @Benchmark
    @Warmup(iterations = 10)
    @Measurement(iterations = 20)
    public void test(){
        dealGet("http://localhost:9999",false);

    }

    public static void _main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + BenchmarkTest.class.getSimpleName() + ".*")
                .forks(1)
                .build();
        new Runner(opt).run();
    }
    private String dealGet(String url,boolean read){
        BufferedReader br = null;
        InputStream is = null;
        StringBuilder sbuf = new StringBuilder();
        try {
            URL reqURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) reqURL.openConnection(); // 进行连接，但是实际上getrequest要在下一句的connection.getInputStream() 函数中才会真正发到服务器
            connection.setDoOutput(false);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(200);
            connection.setDoInput(true);
            connection.connect();
            if (read) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sbuf.append(line).append("\n");
                }
            } else {
                is = connection.getInputStream();

            }
        } catch (IOException e) {
            System.out.println("连接服务器'" + url + "'时发生错误：" + e.getMessage());
        } finally {
            try {
                if (null != br) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sbuf.toString();
    }

//    private String dealPost(String url){
//
//    }
}



