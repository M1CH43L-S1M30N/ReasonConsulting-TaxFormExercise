package consulting.reason.tax_forms_api.controller;

import consulting.reason.tax_forms_api.AbstractControllerTest;
import consulting.reason.tax_forms_api.dto.TaxFormDetailsDto;
import consulting.reason.tax_forms_api.dto.TaxFormDto;
import consulting.reason.tax_forms_api.dto.request.TaxFormDetailsRequest;
import consulting.reason.tax_forms_api.service.TaxFormService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TaxFormController.class)
public class TaxFormControllerTest extends AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    private TaxFormService taxFormService;
    private final TaxFormDetailsDto taxFormDetailsDto = TaxFormDetailsDto.builder()
            .ratio(0.5)
            .assessedValue(100)
            .appraisedValue(1000L)
            .comments("testing")
            .build();
    private final TaxFormDetailsRequest taxFormDetailsRequest = TaxFormDetailsRequest.builder()
            .ratio(0.5)
            .assessedValue(100)
            .appraisedValue(1000L)
            .comments("testing")
            .build();

    private final TaxFormDetailsRequest taxFormDetailsRequestInvalidFields = TaxFormDetailsRequest.builder()
            .ratio(2.0)
            .assessedValue(1000000)
            .appraisedValue(1000L)
            .comments("testing")
            .build();
    private final TaxFormDto taxFormDto = TaxFormDto.builder()
            .id(1)
            .details(taxFormDetailsDto)
            .formName("Testing form RCC")
            .formYear(2024)
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build();

    @Test
    void testFindAllByYear() throws Exception {
        given(taxFormService.findAllByYear(2024)).willReturn(List.of(taxFormDto));

        mockMvc.perform(get(Endpoints.FORMS)
                        .param("year", "2024")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(taxFormDto))));
    }

    @Test
    void testFindById() throws Exception {
        given(taxFormService.findById(taxFormDto.getId())).willReturn(Optional.of(taxFormDto));

        mockMvc.perform(get(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(taxFormDto)));
    }

    @Test
    void testFindByIdHandlesNotFound() throws Exception {
        mockMvc.perform(get(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSave() throws Exception {
        given(taxFormService.save(taxFormDto.getId(), taxFormDetailsRequest)).willReturn(Optional.of(taxFormDto));

        mockMvc.perform(patch(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .content(objectMapper.writeValueAsString(taxFormDetailsRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(taxFormDto)));
    }

    @Test
    void testSaveHandlesNotFound() throws Exception {
        mockMvc.perform(patch(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .content(objectMapper.writeValueAsString(taxFormDetailsRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveHandlesInvalidFields() throws Exception {
        ResultActions resultActions = mockMvc.perform(patch(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .content(objectMapper.writeValueAsString(taxFormDetailsRequestInvalidFields))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        Assert.notNull(responseString, "Random message");
    }
}
