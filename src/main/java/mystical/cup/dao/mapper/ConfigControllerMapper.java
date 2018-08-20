package mystical.cup.dao.mapper;

import java.util.List;
import mystical.cup.model.database.ConfigController;
import mystical.cup.model.database.ConfigControllerExample;
import org.apache.ibatis.annotations.Param;

public interface ConfigControllerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    int countByExample(ConfigControllerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    int deleteByExample(ConfigControllerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    int insert(ConfigController record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    int insertSelective(ConfigController record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    List<ConfigController> selectByExample(ConfigControllerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    ConfigController selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ConfigController record, @Param("example") ConfigControllerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ConfigController record, @Param("example") ConfigControllerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ConfigController record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_controller
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ConfigController record);
}