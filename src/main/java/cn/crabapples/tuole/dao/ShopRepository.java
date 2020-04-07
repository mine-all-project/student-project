package cn.crabapples.tuole.dao;

import cn.crabapples.tuole.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO 销售信息持久层
 *
 * @author Mr.He
 * 2019/7/4 1422:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {

    Shop findAllByKeyWord(String video);

}
