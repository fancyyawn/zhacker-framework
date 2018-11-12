package top.zhacker.sample.consumer.client;


import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

//FeignClient能否代理同一个服务一下的不同api，并采用不同的配置？
@FeignClient(name = "file-server", url = "http://localhost:8012", configuration = FileUploadFeignService.FeignMultipartSupportConfig.class)
public interface FileUploadFeignService {

    /***
     * 1.produces,consumes必填
     * 2.注意区分@RequestPart和RequestParam，不要将
     * @RequestPart(value = "file") 写成@RequestParam(value = "file")
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/uploadFile/server",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<byte[]> fileUpload(@RequestPart(value = "file") MultipartFile file);

    
    class FeignMultipartSupportConfig {

        @Bean
        @Primary
        @Scope("prototype")
        public Encoder multipartFormEncoder() {
            return new SpringFormEncoder();
        }
    }

}
