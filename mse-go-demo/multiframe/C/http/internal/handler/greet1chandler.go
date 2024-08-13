package handler

import (
	"errors"
	"net/http"

	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/http/internal/logic"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/http/internal/svc"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/http/internal/types"
	"github.com/zeromicro/go-zero/rest/httpx"
)

func greet1cHandler(svcCtx *svc.ServiceContext) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		req := types.GreetReq{}
		req.Name = r.URL.Query().Get("name")
		req.CallType = r.URL.Query().Get("callType")
		if req.Name == "" {
			httpx.ErrorCtx(r.Context(), w, errors.New("name is empty"))
			return
		}

		l := logic.NewGreet1cLogic(r.Context(), svcCtx)
		resp, err := l.Greet1c(&req)
		if err != nil {
			httpx.ErrorCtx(r.Context(), w, err)
		} else {
			httpx.OkJsonCtx(r.Context(), w, resp)
		}
	}
}
