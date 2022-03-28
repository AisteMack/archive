package com.dokobit.archive.service

import com.dokobit.archive.exception.ValidationErrorException
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import static java.util.Arrays.*

class ZipServiceTest extends Specification {

    def compressorService = new ZipService()

    def "Should throw exception when file name is null"() {
        given:
        MultipartFile multipartFile = new MockMultipartFile("test", "test".getBytes())
        def os = Mock(OutputStream)
        when:
        compressorService.zipFiles(os, asList(multipartFile))
        then:
        ValidationErrorException ex = thrown()
        ex.getResponse().getMessage() == "File name is missing."
    }

    def "Should throw exception when file name is empty"() {
        given:
        MultipartFile multipartFile = new MockMultipartFile("test", "", null, "test".getBytes())
        def os = Mock(OutputStream)
        when:
        compressorService.zipFiles(os, asList(multipartFile))
        then:
        ValidationErrorException ex = thrown()
        ex.getResponse().getMessage() == "File name is missing."
    }

    def "Should throw exception when file name is blank"() {
        given:
        MultipartFile multipartFile = new MockMultipartFile("test", "  ", null, "test".getBytes())
        def os = Mock(OutputStream)
        when:
        compressorService.zipFiles(os, asList(multipartFile))
        then:
        ValidationErrorException ex = thrown()
        ex.getResponse().getMessage() == "File name is missing."
    }

    def "Should throw exception when max file size exceeded"() {
        given:
        byte[] bytes = new byte[1000001]
        MultipartFile multipartFile = new MockMultipartFile("test", "test.txt", null, bytes)
        def os = Mock(OutputStream)
        when:
        compressorService.zipFiles(os, asList(multipartFile))
        then:
        ValidationErrorException ex = thrown()
        ex.getResponse().getMessage() == "File too large."
    }

    def "Should throw exception when output stream throws IOException"() {
        given:
        byte[] bytes = new byte[1000000]
        MultipartFile multipartFile = new MockMultipartFile("test", "test.txt", null, bytes)
        def os = Mock(OutputStream)
        os.close() >> {throw new IOException()}
        when:
        compressorService.zipFiles(os, asList(multipartFile))
        then:
        ValidationErrorException ex = thrown()
        ex.getResponse().getMessage() == "Failed to archive files."
    }

    def "Should not throw exception when file is correct"() {
        given:
        byte[] bytes = new byte[1000000]
        MultipartFile multipartFile = new MockMultipartFile("test", "test.txt", null, bytes)
        def os = Mock(OutputStream)
        when:
        compressorService.zipFiles(os, asList(multipartFile))
        then:
        noExceptionThrown()
    }
}
