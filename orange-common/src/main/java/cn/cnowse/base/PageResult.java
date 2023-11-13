package cn.cnowse.base;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页结果
 *
 * @author Jeong Geol
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private Long pageNum;
    private Long pageSize;
    private Long total;
    private List<T> data;

}
