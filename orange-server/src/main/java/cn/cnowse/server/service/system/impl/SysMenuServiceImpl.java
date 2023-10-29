package cn.cnowse.server.service.system.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.constant.Constants;
import cn.cnowse.constant.UserConstants;
import cn.cnowse.server.mapper.system.SysMenuMapper;
import cn.cnowse.server.pojo.system.entity.SysMenu;
import cn.cnowse.server.pojo.system.vo.MetaVO;
import cn.cnowse.server.pojo.system.vo.RouterVO;
import cn.cnowse.server.service.system.SysMenuService;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private static final String SLASH = "/";

    @Override
    public List<SysMenu> getMenuTreeByUserId(Long userId) {
        List<SysMenu> menus;
        if (userId != null && userId == 1L) {
            menus = baseMapper.selectMenuTreeAll();
        } else {
            menus = baseMapper.selectMenuTreeByUserId(userId);
        }
        return this.getChildPerms(menus);
    }

    @Override
    public List<RouterVO> buildMenus(List<SysMenu> menus) {
        List<RouterVO> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            RouterVO router = new RouterVO();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(this.getRouteName(menu));
            router.setPath(this.getRouterPath(menu));
            router.setComponent(this.getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()),
                    menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVO> childrenList = new ArrayList<>();
                RouterVO children = new RouterVO();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(),
                        StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && this.isInnerLink(menu)) {
                router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon()));
                router.setPath(SLASH);
                List<RouterVO> childrenList = new ArrayList<>();
                RouterVO children = new RouterVO();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    @Override
    public Set<String> getMenuPermsByRoleId(Long roleId) {
        List<String> perms = baseMapper.selectMenuPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public Set<String> getMenuPermsByUserId(Long userId) {
        List<String> perms = baseMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    private String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !this.isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0
                && this.isInnerLink(menu)) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && this.isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    private String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && this.isInnerLink(menu)) {
            routerPath = this.innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = SLASH + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = SLASH;
        }
        return routerPath;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    private String getRouteName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (this.isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return 替换后的内链域名
     */
    private String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[] {Constants.HTTP, Constants.HTTPS, Constants.WWW, "."},
                new String[] {"", "", "", SLASH});
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    private boolean isInnerLink(SysMenu menu) {
        return menu.getIsFrame().equals(UserConstants.NO_FRAME)
                && StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS);
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    private boolean isMenuFrame(SysMenu menu) {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    private boolean isParentView(SysMenu menu) {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 根据父节点的 ID 获取所有子节点
     *
     * @param list 分类表
     * @return String
     */
    private List<SysMenu> getChildPerms(List<SysMenu> list) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu t : list) {
            // 根据传入的某个父节点 ID,遍历该父节点的所有子节点
            if (t.getParentId() == 0) {
                this.recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param t 子节点
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = this.getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (this.hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<>();
        for (SysMenu n : list) {
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return !this.getChildList(list, t).isEmpty();
    }

}
