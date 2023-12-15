package com.alibaba.arms.mock.api;

import com.alibaba.arms.mock.api.dto.BigKeyTroubleRequest;
import com.alibaba.arms.mock.api.dto.ErrorTroubleRequest;
import com.alibaba.arms.mock.api.dto.SlowTroubleRequest;
import com.alibaba.arms.mock.api.dto.StupidTroubleRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * @author aliyun
 * @date 2021/06/10
 */
public interface IComponentAPI {

    @POST(value = "/components/api/v1/{component}/{action}")
    Call<String> invokeChildren(@Path("component") String component, @Path("action") String action,
                                @Body List<Invocation> children, @Query("level") int level);

    @POST(value = "/components/api/v1/{component}/{action}")
    Call<String> invokeChildren(@Path("component") String component, @Path("action") String action);

    @POST(value = "/case/api/v1/{caseName}/execute")
    Call<String> startCase(@Path("caseName") String caseName, @Body Invocation invocation, @Query("level") int level);

    @POST(value = "/case/api/v1/test/{test}")
    Call<String> testCase(@Path("caseName") String caseName, @Body Invocation invocation, @Query("level") int level, @Path("test") String test);

    @POST(value = "/trouble/make/slow")
    Call<String> injectSlow(@Body SlowTroubleRequest slowTroubleRequest);

    @POST(value = "/trouble/make/big")
    Call<String> injectBig(@Body BigKeyTroubleRequest bigKeyTroubleRequest);

    @POST(value = "/trouble/make/error")
    Call<String> injectError(@Body ErrorTroubleRequest errorTroubleRequest);

    @POST(value = "/trouble/make/cancel")
    Call<String> cancelInject(@Query("troubleCode") String troubleCode, @Query("componentName") String componentName);

}
