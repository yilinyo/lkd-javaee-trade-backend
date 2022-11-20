package com.yilin.trade.service.impl;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yilin.trade.common.ErrorCode;
import com.yilin.trade.exception.BusinessException;
import com.yilin.trade.mapper.GoodsMapper;
import com.yilin.trade.mapper.UserMapper;
import com.yilin.trade.model.GoodsInfo;
import com.yilin.trade.model.domain.Goods;
import com.yilin.trade.model.request.GoodsSearchRequest;
import com.yilin.trade.service.GoodsService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 13227
* @description 针对表【goods】的数据库操作Service实现
* @createDate 2022-11-09 17:12:44
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getGoodsList() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();


        //查询所有商品

        List<Goods> list = goodsMapper.selectList(queryWrapper);

        //获取商品数量大于0
        List<Goods> newList = new ArrayList<>();
        for(Goods g:list){

            if(g.getGoodsNum()>0){

                newList.add(g);
            }

        }


        return newList;

    }

    @Override
    public List<Goods> getGoodsListByName(String searchParam) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("goodsName",searchParam);

        List<Goods> list = goodsMapper.selectList(queryWrapper);


        //获取商品数量大于0
        List<Goods> newList = new ArrayList<>();
        for(Goods g:list){

            if(g.getGoodsNum()>0){

                newList.add(g);
            }

        }


        return newList;


    }

    @Override
    public List<Goods> getGoodsListByCondition(GoodsInfo goodsSearchRequest) {


        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();


        // 反射
        Field[] fields = goodsSearchRequest.getClass().getDeclaredFields();

        for(int i = 0 ; i < fields.length ; i++) {
            //设置是否允许访问，不是修改原来的访问权限修饰词。
            //爆破
            fields[i].setAccessible(true);

            try {
                // 是否为null 或空

                if(fields[i].get(goodsSearchRequest)!=null && StringUtils.isNotBlank(fields[i].get(goodsSearchRequest).toString())){

                    // 模糊查询 拼接

                    queryWrapper.like(fields[i].getName(),fields[i].get(goodsSearchRequest));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return  goodsMapper.selectList(queryWrapper);
    }



    @Override
    public List<Goods> getMyGoodsListByCondition(GoodsSearchRequest goodsSearchRequest) {



        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        Long uid = goodsSearchRequest.getUid();
        queryWrapper.eq("uid",uid);


        // 反射
        Field[] fields = goodsSearchRequest.getClass().getDeclaredFields();

        for(int i = 0 ; i < fields.length ; i++) {
            //设置是否允许访问，不是修改原来的访问权限修饰词。
            //爆破
            fields[i].setAccessible(true);

            try {
                // 是否为null 或空

                if(fields[i].get(goodsSearchRequest)!=null && StringUtils.isNotBlank(fields[i].get(goodsSearchRequest).toString())){

                    // 模糊查询 拼接

                    queryWrapper.like(fields[i].getName(),fields[i].get(goodsSearchRequest));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return  goodsMapper.selectList(queryWrapper);

    }

    @Override
    public Goods getGoodsById(Long gid) {


        Goods goods = goodsMapper.selectById(gid);

        //这里我分开查询，以后学了mybatis连接表再连接查询吧

        return goods;


    }

    @Override
    public Goods savaGoods(Goods goods) {
        int id = goodsMapper.insert(goods);
        return goods;

    }

    @Override
    public boolean checkNum(Long gid, Integer num) {
        Goods goodsById = getGoodsById(gid);
        Long goodsNum = goodsById.getGoodsNum();

        if(goodsNum-(long)num >=0){

            return true;
        }else{

            return false;
        }


    }

    @Override
    public void buyGoods(Long gid, Integer num) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        Goods goodsById = getGoodsById(gid);

        queryWrapper.eq("gid",gid);


        goodsById.setGoodsNum(goodsById.getGoodsNum()-(long)num);


        int update = goodsMapper.update(goodsById, queryWrapper);


        if(update<=0){

            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }




    }

    @Override
    public void editGoods(Goods newGoods,Long gid) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("gid",gid);
        newGoods.setUpdateTime(String.valueOf(System.currentTimeMillis()));


        int update = goodsMapper.update(newGoods, queryWrapper);
        if(update<=0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

    }

    @Override
    public void delGoods(Long gid) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("gid",gid);

        //逻辑删除

        goodsMapper.delete(queryWrapper);
        int update = goodsMapper.delete(queryWrapper);



    }

    @Override
    public int getGoodsNum(Long uid) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("uid",uid);

        List<Goods> list = goodsMapper.selectList(queryWrapper);

        return list.size();
    }
}




