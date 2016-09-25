package org.jeewx.api.wxstore.group;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeewx.api.core.common.JSONHelper;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.wxstore.group.model.Group;
import org.jeewx.api.wxstore.group.model.GroupDetailInfo;
import org.jeewx.api.wxstore.group.model.GroupProductInfo;
import org.jeewx.api.wxstore.group.model.GroupRtnInfo;

/**
 * 微信小店 - 分组
 * @author zhangdaihao
 *
 */
public class JwGroupManangerAPI {
	// 增加分组
	private static String create_group_url = "https://api.weixin.qq.com/merchant/group/add?access_token=ACCESS_TOKEN";
	// 修改分组属性
	private static String update_group_url = "https://api.weixin.qq.com/merchant/group/propertymod?access_token=ACCESS_TOKEN";
	// 根据分组ID获取分组信息
	private static String getid_group_url = "https://api.weixin.qq.com/merchant/group/getbyid?access_token=ACCESS_TOKEN";
	// 删除分组
	private static String del_group_url = "https://api.weixin.qq.com/merchant/group/del?access_token=ACCESS_TOKEN";
	// 获取所有分组
	private static String getall_group_url = "https://api.weixin.qq.com/merchant/group/getall?access_token=ACCESS_TOKEN";
	// 修改分组商品
	private static String update_productmod_url = "https://api.weixin.qq.com/merchant/group/productmod?access_token=ACCESS_TOKEN";
	
	/**
	 * 增加分组
	 * @param group
	 * @return
	 */
	public static GroupRtnInfo doAddGroupManager(String newAccessToken, Group group) {
		if (newAccessToken != null) {
			String requestUrl = create_group_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.fromObject(group);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			GroupRtnInfo groupRtnInfo = (GroupRtnInfo)JSONObject.toBean(result, GroupRtnInfo.class);
			return groupRtnInfo;
		}
		return null;
	}
	
	/**
	 * 删除分组
	 * @param group_id
	 * @return
	 */
	public static GroupRtnInfo doDelGroupManager(String newAccessToken, Integer group_id) {
		if (newAccessToken != null) {
			String requestUrl = del_group_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = "{\"group_id\": "+group_id+"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			GroupRtnInfo groupRtnInfo = (GroupRtnInfo)JSONObject.toBean(result, GroupRtnInfo.class);
			return groupRtnInfo;
		}
		return null;
	}
	
	/**
	 * 修改分组属性
	 * @param group
	 * @return
	 */
	public static GroupRtnInfo doUpdateGroupManagerProperties(String newAccessToken, Group group) {
		if (newAccessToken != null) {
			String requestUrl = update_group_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.fromObject(group);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			GroupRtnInfo groupRtnInfo = (GroupRtnInfo)JSONObject.toBean(result, GroupRtnInfo.class);
			return groupRtnInfo;
		}
		return null;
	}
	
	/**
	 * 修改分组商品
	 * @param groupProductInfo
	 * @return
	 */
	public static GroupRtnInfo doUpdateGroupManagerProduct(String newAccessToken, GroupProductInfo groupProductInfo) {
		if (newAccessToken != null) {
			String requestUrl = update_productmod_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject obj = JSONObject.fromObject(groupProductInfo);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", obj.toString());
			GroupRtnInfo groupRtnInfo = (GroupRtnInfo)JSONObject.toBean(result, GroupRtnInfo.class);
			return groupRtnInfo;
		}
		return null;
	}
	
	/**
	 * 获取所有分组
	 * @return
	 */
	public static List<GroupDetailInfo> getAllGroup(String newAccessToken) {
		if (newAccessToken != null) {
			String requestUrl = getall_group_url.replace("ACCESS_TOKEN", newAccessToken);
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "GET", null);
			// 正常返回
			List<GroupDetailInfo> groupsDetailInfo = null;
			JSONArray info = result.getJSONArray("groups_detail");
			groupsDetailInfo = JSONHelper.toList(info, GroupDetailInfo.class);
			return groupsDetailInfo;
		}
		return null;
	}
	
	/**
	 * 根据分组ID获取分组信息
	 * @return
	 */
	public static GroupDetailInfo getByGroupId(String newAccessToken, Integer group_id) {
		if (newAccessToken != null) {
			String requestUrl = getid_group_url.replace("ACCESS_TOKEN", newAccessToken);
			String json = "{\"group_id\": "+group_id+"}";
			JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", json);
			// 正常返回
			GroupDetailInfo groupDetailInfo = null;
			JSONObject info = result.getJSONObject("group_detail");
			groupDetailInfo = (GroupDetailInfo)JSONObject.toBean(info, GroupDetailInfo.class);
			return groupDetailInfo;
		}
		return null;
	}
}
 