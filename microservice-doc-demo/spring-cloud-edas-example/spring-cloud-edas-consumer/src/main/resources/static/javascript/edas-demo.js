var docs = new Ractive({
    target: '.docs-container',
    template: '#docs',
    data: {
        docs: [
            {
                link: 'https://help.aliyun.com/product/29500.html',
                title: '企业级分布式应用服务 EDAS'
            },
            {
                link: 'https://help.aliyun.com/document_detail/98591.html',
                title: '如何进行 Spring Cloud 应用开发'
            },
            {
                link: 'https://help.aliyun.com/document_detail/99299.html',
                title: '如何进行 Dubbo 应用开发'
            },
            {
                link: 'https://help.aliyun.com/document_detail/131052.html',
                title: '使用 Cloud Toolkit 实现本地与云上联调'
            },
            {
                link: 'https://help.aliyun.com/document_detail/112390.html',
                title: '最佳实践：使用启动模板创建实例'
            },
            {
                link: 'https://help.aliyun.com/product/113017.html',
                title:
                    '在阿里云上托管其他语言(Php, Python, .net core, NodeJs, Go, Ruby等)的程序'
            }
        ]
    }
})

var Detection = Ractive.extend({
    template: '#detection',
    rpcEchoString(echoString) {
        fetch('/consumer-echo/' + echoString)
            .then(res => res.text())
            .then(data => {
                var timestamp = /\d{13}/g;
                data = data.replace(timestamp, (b, c) =>
                    b == 'undefined' ? '' : new Date(+b).toISOString() + " : "
                )
                this.set('respondedString', data)
            })
    },
    componentDidMount() {
        fetch('/ping')
            .then(res => res.json())
            .then(data => this.set('serviceExisted', data))
            .finally(data => this.set('loading', false))
    }
})

var detection = new Detection({
    target: '.detection-result',
    data: {
        loading: true,
        serviceExisted: false,
        echoString: 'Echo this string',
        respondedString: ''
    }
})
detection.componentDidMount()
