package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.constant.ProductConstant;
import com.atguigu.common.constant.ProductConstant.AttrEnum;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.vo.AttrRespVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.w3c.dom.Attr;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryService categoryService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional //?????????????????????????????????
    @Override
    public void saveAttr(AttrVo attr) {
        //??????????????????????????? ????????????attrEntity??????
        AttrEntity attrEntity = new AttrEntity();
        //??????????????????????????????????????????????????????attrEntity?????????, ?????????????????????????????????
        BeanUtils.copyProperties(attr, attrEntity);
        //1. ??????????????????
        this.save(attrEntity);
        //2. ??????????????????
        //base?????????????????????????????????
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId()!=null){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            //??????attrGroupId attrId
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type){
        //???????????? ???????????????base 1 ?????????0
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(type)?
                                                ProductConstant. AttrEnum.ATTR_TYPE_BASE.getCode():
                                                ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        //??????????????????id??????
        if(catelogId != 0){
            //
            queryWrapper.eq("catelog_id", catelogId);
        }
        //????????????????????????
        String key = (String) params.get("key");
        //??????????????????
        if(!StringUtils.isEmpty(key)){
            //????????????????????????
            queryWrapper.and((wrapper)->{
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });

        }
        IPage<AttrEntity> page = this.page(

                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        //????????????????????????
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        //?????????????????????, ??????attrId ?????????????????????attr_id???attr<=>attrGroup?????????????????????
        //?????????????????????????????????attrGroupId
        //attrGroupId => attrgroupEntity =>attrGroupName
        //attrId => categoryEntity => catelogName

        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            //1. ??????????????????????????????
            //??????????????????????????????????????????, ??????????????????????????????????????????
            if("base".equalsIgnoreCase(type)){
                AttrAttrgroupRelationEntity attrId = relationDao.selectOne(
                        new QueryWrapper<AttrAttrgroupRelationEntity>()
                                .eq("attr_id", attrEntity.getAttrId()));
                if (attrId != null && attrId.getAttrGroupId()!=null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }


            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }

            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, respVo);

        //????????????
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //??????????????????
            //????????? ?????? ??? ????????????????????????
            AttrAttrgroupRelationEntity attrGroupRelation = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if(attrGroupRelation!=null){
                //?????????????????????????????????id ????????????respVo
                respVo.setAttrGroupId(attrGroupRelation.getAttrGroupId());
                //??????Dao????????????????????????
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupRelation.getAttrGroupId());
                if(attrGroupEntity!=null){
                    //??????????????????????????????????????? ????????????respVo
                    respVo.setAttrName(attrGroupEntity.getAttrGroupName());
                }
            }
        }



        //??????????????????
        //?????????????????????????????????id
        Long catelogId = attrEntity.getCatelogId();
        //????????????service?????????????????? ????????????id????????????????????????????????????Long??????
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        respVo.setCatelogPath(catelogPath);
        //????????????Dao???????????????id?????????????????????,
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if(categoryEntity!=null){
            //???????????????????????????
            respVo.setCatelogName(categoryEntity.getName());
        }

        return respVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
        //????????????
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //??????????????????
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());
            //???????????????????????? attr_id???????????????
            Integer count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if(count>0){
                //????????????
                //UPDATE `pms_attr_attrgroup_relation` SET attr_group_id = ? WHERE attr_id=?
                relationDao.update(relationEntity, new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            }else{
                //??????
                relationDao.insert(relationEntity);
            }
        }

    }

    /**
     * P80 ????????????id?????????????????????????????????
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        //?????? ????????????id attrgroupId ???????????????????????????????????????????????????????????????
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        //??????????????????????????????????????????attrid????????????
        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
        //????????????attrIds????????????
        if(attrIds==null || attrIds.size()==0){
            return null;
        }
        //????????????id????????????????????????????????????
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        //??????????????????
        return (List<AttrEntity>) attrEntities;
    }

    /**
     * P80 ???????????????????????????????????????????????????
     * @param vos
     */
    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        //????????????????????????attrId ??? attrGroupId???????????????, ????????????????????????, ??????????????????????????????????????????
        //?????? attrId attrGroupId ?????????AttrAttrGroupRelationEntity????????? ??????????????????????????????
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        //???????????????????????????, ???????????????????????????????????????????????????attrId ??? attrGroupId???????????????
        relationDao.deleteBatchRelation(entities);
    }

    /**
     * ?????????????????????????????????????????????
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1. ??????????????????????????????????????????????????????????????????
        //??????????????????id??????????????????????????????
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        //???????????????????????????????????????id
        Long catelogId = attrGroupEntity.getCatelogId();
        //2. ?????????????????????????????????????????????????????????
        //2.1 ??????????????????????????????
        List<AttrGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> collect = group.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        //2.2 ???????????????????????????
        List<AttrAttrgroupRelationEntity> groupId = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
        //??????????????????????????????id??????
        List<Long> attrIds = groupId.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        //2.3 ???????????????????????????????????????????????????
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(attrIds!=null && attrIds.size()>0){
            wrapper.notIn("attr_id", attrIds);
        }
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }


}