//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus'
import router from '@/router';
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})


//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        //判断业务状态码
        if(result.data.code === 1){
            return result.data;
        }
        //alert(result.data.msg?result.data.msg:'服务异常');
        ElMessage.error(result.data.msg?result.data.msg:'服务异常');
        //异步操作转换为失败状态
        return Promise.reject(result.data);
    },
    err=>{
        console.log(err.response.status);
        //判断响应状态码，如果为401（未登录），则跳转到登录页面
        if(err.response.status === 401){
            ElMessage.error('未登录，请先登录');
            router.push('/login');
        }else{
            ElMessage.error('服务异常');
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)
import { useTokenStore } from '@/stores/token';

//添加请求拦截器
instance.interceptors.request.use(
    //请求前的回调
    config=>{
        const tokenStore = useTokenStore();
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    //请求错误的回调
    err=>{
        Promise.reject(err);
    }
)

export default instance;