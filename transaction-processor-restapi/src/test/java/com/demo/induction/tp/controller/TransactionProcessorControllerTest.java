package com.demo.induction.tp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.demo.induction.tp.constant.Constants.AMOUNT;
import static com.demo.induction.tp.constant.Constants.TYPE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionProcessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnSuccessStatus_importCsvTransactions() throws Exception {
        Path path = Paths.get("src/test/resources/valid_transactions.csv");
        String name = "file";
        String originalFileName = "valid_transactions.csv";
        String contentType = "text/plain";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(name, originalFileName,
                contentType, Files.readAllBytes(path));
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/importcsvtransactions")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldReturnBadRequest_importCsvTransactions() throws Exception {
        Path path = Paths.get("src/test/resources/invalid_transactions.csv");
        String name = "file";
        String originalFileName = "invalid_transactions.csv";
        String contentType = "text/plain";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(name, originalFileName,
                contentType, Files.readAllBytes(path));
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/importcsvtransactions")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].order", is(5)))
                .andExpect(jsonPath("$.violations[0].property", is(AMOUNT)))
                .andExpect(jsonPath("$.balanced", is(false)))
                .andDo(print());
    }

    @Test
    public void shouldReturnSuccessStatus_importXmlTransactions() throws Exception {
        Path path = Paths.get("src/test/resources/valid_transactions.xml");
        String name = "file";
        String originalFileName = "valid_transactions.xml";
        String contentType = "text/plain";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(name, originalFileName,
                contentType, Files.readAllBytes(path));
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/importxmltransactions")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldReturnBadRequest_importXmlTransactions() throws Exception {
        Path path = Paths.get("src/test/resources/invalid_transactions.xml");
        String name = "file";
        String originalFileName = "invalid_transactions.xml";
        String contentType = "text/plain";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(name, originalFileName,
                contentType, Files.readAllBytes(path));
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/importxmltransactions")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].order", is(2)))
                .andExpect(jsonPath("$.violations[0].property", is(TYPE)))
                .andExpect(jsonPath("$.balanced", is(false)))
                .andDo(print());
    }

}