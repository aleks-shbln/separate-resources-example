package io.ashabalin.ohtp.separateresources.pdf;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class PdfGenerator {

    public byte[] generatePdf() {
        try {
            final ClassPathResource htmlResource = new ClassPathResource("/html/index.html");
            final String htmlContent = new BufferedReader(new InputStreamReader(htmlResource.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));

            try (final ByteArrayOutputStream pdfStream = new ByteArrayOutputStream()) {
                new PdfRendererBuilder()
                        .useProtocolsStreamImplementation(new ClassPathStreamFactory(), "classpath")
                        .useFastMode()
                        .withHtmlContent(htmlContent, "classpath:/html/")
                        .toStream(pdfStream)
                        .run();
                return pdfStream.toByteArray();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
