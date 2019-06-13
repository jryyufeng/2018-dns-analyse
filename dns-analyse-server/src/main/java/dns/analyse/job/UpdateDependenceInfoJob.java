package dns.analyse.job;

import dns.analyse.service.tools.LogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/30
 * @time: 15:52
 */
@Component
@Lazy(false)
@Slf4j
public class UpdateDependenceInfoJob {

    //更新数据，每一个月一次，每次2点开始，一次五万条  例子
    @LogAnnotation(title = "依赖数据更新",action = "更新依赖数据，树，ip")
    @Scheduled(cron = "0 0 2 3 1/1 * ")
    public void updateDependenceInfo(){
        String command = "python F:\\biyedocument\\PycharmProjects\\dns-analyse\\dependence-analyse\\QueryDns.py 1 2";
        try{
            Process p = Runtime.getRuntime().exec(command);
            InputStream is = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if (p.exitValue() != 0) {
                //说明命令执行失败
                while ((reader.readLine()) != null) {
                    log.error(reader.readLine());
                }
            }

        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

    //更新图信息


}
