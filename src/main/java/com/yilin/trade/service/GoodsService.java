package com.yilin.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yilin.trade.model.GoodsInfo;
import com.yilin.trade.model.domain.Goods;
import com.yilin.trade.model.request.GoodsSearchRequest;

import java.util.List;


/**
* @author 13227
* @description 针对表【goods】的数据库操作Service
* @createDate 2022-11-09 17:12:44
*/
public interface GoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     * @return List<Goods>
     */
    List<Goods> getGoodsList();

    /**
     * 获取商品列表根据商品名字
     * @return List<Goods>
     */
    List<Goods> getGoodsListByName(String searchParam);

    /**
     * 获取商品列表根据条件
     * @return List<Goods>
     */
    public List<Goods> getGoodsListByCondition(GoodsInfo goodsSearchRequest);

    /**
     * 获取我的商品列表 根据条件
     * @return List<Goods>
     */
    public List<Goods> getMyGoodsListByCondition(GoodsSearchRequest goodsSearchRequest);

    /**
     * 根据商品id获取商品
     * @return   Goods
     */
    Goods getGoodsById(Long gid);

    /**
     * 保存商品
     * @param goods
     * @return Goods
     */

    Goods savaGoods(Goods goods);

    /**
     *  检查商品库存是否足够
     */

    boolean checkNum(Long gid,Integer num);


    /**
     *  减库存 购买
     * @param gid
     * @param num
     */

    void buyGoods(Long gid,Integer num);



    /**
     * 修改商品信息
     *
     * @param newGoods
     * @param gid
     */
    void editGoods(Goods newGoods,Long gid);


    /**
     * 下架商品
     */
    void delGoods(Long gid);


    /**
     * 根据gid 获得商品数量
     */
    int getGoodsNum(Long uid);








}
