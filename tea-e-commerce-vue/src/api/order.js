//导入request.js工具
import request from '@/utils/request.js'
export const getOrderListService = ()=>{
    return request.get('/admin/orderList')
}
export const updateStateService = (state,id)=>{
    return request.put('/admin/orderUpdate', { 
        state: state, 
        id: id 
    });
}
export const searchOrderService = (orderReference)=>{
    return request.get(`/admin/orderSearch/${orderReference}`);
}