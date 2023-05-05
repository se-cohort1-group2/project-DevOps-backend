package sg.edu.ntu.m3project.m3project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.interceptor.Interceptor;
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository;
import sg.edu.ntu.m3project.m3project.service.ConcertService;
import sg.edu.ntu.m3project.m3project.service.UserService;

@WebMvcTest(ConcertController.class)
public class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Interceptor interceptor;

    @MockBean
    private ConcertService mockService;

    @MockBean
    private ConcertRepository mockRepo;

    @BeforeEach
	void initTest() throws Exception{
		when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
	}

    @Test
    public void givenDataExist_whenFetchAll_thenReturnOk() throws Exception {

        // mock repo layer
        List<ConcertEntity> currentConcertList = (List<ConcertEntity>) mockRepo.findAll();
        when(mockRepo.findAll()).thenReturn(currentConcertList);

        // call the method
        this.mockMvc.perform(get("/concerts")).andDo(print()).andExpect(status().isNotFound());
    }

}
