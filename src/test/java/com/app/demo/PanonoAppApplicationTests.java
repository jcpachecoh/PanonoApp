package com.app.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PanonoAppApplicationTests {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	private MockMvc mockMvc;
    private List<UploadPanorama> panoramaList = new ArrayList<>();
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Test
    public void getPanoramas() throws Exception {
    	Timestamp now = new Timestamp(System.currentTimeMillis());
    	UploadPanorama first = new UploadPanorama();
    	first.setId(1);
    	first.setNumberPanoramas(2);
    	first.setTmp(now);
    	
    	UploadPanorama second = new UploadPanorama();
    	second.setId(2);
    	second.setNumberPanoramas(2);
    	second.setTmp(now);
    	
    	mockMvc.perform(get(UploadPanoramaConst.GET_ALL_PANORAMAS))
    					.andExpect(status().isOk())
    					.andExpect(content().contentType(APPLICATION_JSON))
    					.andExpect(jsonPath("$", hasSize(2)))
    					.andExpect(jsonPath("$[0].id", is(first.getId())));

    }
    
    @Test
    public void getStadistics() throws Exception {
    	Stadistics st = new Stadistics();
    	
    	st.setSum(5);
    	st.setAvg(1.0);
    	st.setMax(5);
    	st.setMin(1);
    	st.setCount(5);
    	mockMvc.perform(get(UploadPanoramaConst.GET_STADISTICS))
    					.andExpect(status().isOk())
    					.andExpect(content().contentType(APPLICATION_JSON))
    					.andExpect(jsonPath("$", hasSize(2)))
    					.andExpect(jsonPath("$[0].sum", is(st.getSum())))
    					.andExpect(jsonPath("$[0].avg", is(st.getAvg())))
    					.andExpect(jsonPath("$[0].max", is(st.getMax())))
    					.andExpect(jsonPath("$[0].min", is(st.getMin())))
    					.andExpect(jsonPath("$[0].count", is(st.getCount())));

    }
    
    @Test
    public void createBookmark() throws Exception {

    	 UploadPanorama up = new UploadPanorama();
    	 up.setNumberPanoramas(3);
        String upJson = json(up);

        this.mockMvc.perform(post(UploadPanoramaConst.CREATE_PANORAMA)
                .contentType(contentType)
                .content(upJson))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
