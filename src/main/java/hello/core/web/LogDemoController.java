package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    //request 스코프 빈 MyLogger는 HTTP request이 있을 때 생성되기 때문에 바로 DI할 경우 오류가 발생한다. 따라서 Provider 사용.
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();
        System.out.println("before call myLogger = " + myLogger);
        myLogger.setRequestURL(requestURL);
        System.out.println("after call myLogger = " + myLogger);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }
}
