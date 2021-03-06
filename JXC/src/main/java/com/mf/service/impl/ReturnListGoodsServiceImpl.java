package com.mf.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mf.entity.ReturnListGoods;
import com.mf.repository.ReturnListGoodsRepository;
import com.mf.service.ReturnListGoodsService;
import com.mf.util.StringUtil;

/**
 * 退货单商品Service实现类
 *
 * @author Administrator
 */
@Service("returnListGoodsService")
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService {

    @Resource
    private ReturnListGoodsRepository returnListGoodsRepository;

    @Override
    public List<ReturnListGoods> listByReturnListId(Integer returnListId) {
        return returnListGoodsRepository.listByReturnListId(returnListId);
    }

    @Override
    public List<ReturnListGoods> list(ReturnListGoods returnListGoods) {
        return returnListGoodsRepository.findAll(new Specification<ReturnListGoods>() {

            @Override
            public Predicate toPredicate(Root<ReturnListGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (returnListGoods != null) {
                    if (returnListGoods.getType() != null && returnListGoods.getType().getId() != null
                            && returnListGoods.getType().getId() != 1) {
                        predicate.getExpressions()
                                .add(cb.equal(root.get("type").get("id"), returnListGoods.getType().getId()));
                    }
                    if (StringUtil.isNotEmpty(returnListGoods.getCodeOrName())) {
                        predicate.getExpressions()
                                .add(cb.or(cb.like(root.get("code"), "%" + returnListGoods.getCodeOrName() + "%"),
                                        cb.like(root.get("name"), "%" + returnListGoods.getCodeOrName() + "%")));
                    }
                }
                return predicate;
            }
        });
    }

}
