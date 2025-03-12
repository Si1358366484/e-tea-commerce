//定义store
import { defineStore } from 'pinia'
import { ref } from 'vue';
//传递两个参数，第一个是store的名称 要有唯一性，第二个是store的内容 函数
export const useTokenStore = defineStore('token', () => {
    //定义状态的内容
    //1.响应式变量
    const token = ref('')
    //2.定义方法,修改token的值
    const setToken = (newToken) => {
        token.value = newToken
    }
    //3.函数 移除token的值
    const removeToken = () => {
        token.value = ''
    }

    return {
        token,
        setToken,
        removeToken
    }
},{
    //持久化存储token
    persist: true
});