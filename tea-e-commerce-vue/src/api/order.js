//导入request.js工具
import request from '@/utils/request.js'
export const getOrderListService = ()=>{
    return request.get('/admin/orderList')
}