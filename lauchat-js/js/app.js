window.app = {
	
	/**
	 * netty服务后端发布的url地址
	 */
	//nettyServerUrl: 'ws://192.168.1.10:8088/ws',
	
	/**
	 * 后端服务发布的url地址
	 */
	//serverUrl: 'http://192.168.1.10:8080',
	
	/**
	 * 图片服务器的url地址
	 */
	//imgServerUrl: 'http://192.168.1.23:88/durex/',
	
	/**
	 * netty服务后端发布的url地址
	 */
	nettyServerUrl: 'ws://47.107.51.37:8088/ws',
	
	/**
	 * 后端服务发布的url地址
	 */
	serverUrl: 'http://47.107.51.37:8080',
	
	/**
	 * 图片服务器的url地址
	 */
	imgServerUrl: 'http://47.107.51.37:88/durex/',
	
	/**
	 * 判断字符串是否为空
	 * @param {Object} str
	 * true：不为空
	 * false：为空
	 */
	isNotNull: function(str) {
		if (str != null && str != "" && str != undefined) {
			return true;
		}
		return false;
	},
	
	/**
	 * 封装消息提示框，默认mui的不支持居中和自定义icon，所以使用h5+
	 * @param {Object} msg
	 * @param {Object} type
	 */
	showToast: function(msg, type) {
		plus.nativeUI.toast(msg, 
			{icon: "image/" + type + ".png", verticalAlign: "center"})
	},
	
	/**
	 * 保存用户的全局对象
	 * @param {Object} user
	 */
	setUserGlobalInfo: function(user) {
		var userInfoStr = JSON.stringify(user);
		plus.storage.setItem("userInfo", userInfoStr);
	},
	
	/**
	 * 获取用户的全局对象
	 */
	getUserGlobalInfo: function() {
		var userInfoStr = plus.storage.getItem("userInfo");
		return JSON.parse(userInfoStr);
	},
	
	/**
	 * 登出后，移除用户全局对象
	 */
	userLogout: function() {
		plus.storage.removeItem("userInfo");
	},
	
	/**
	 * 保存用户的联系人列表
	 * @param {Object} contactList
	 */
	setContactList: function(contactList) {
		var contactListStr = JSON.stringify(contactList);
		plus.storage.setItem("contactList", contactListStr);
	},
	
	/**
	 * 获取本地缓存中的联系人列表
	 */
	getContactList: function() {
		var contactListStr = plus.storage.getItem("contactList");
		
		if (!this.isNotNull(contactListStr)) {
			return [];
		}
		
		return JSON.parse(contactListStr);
	},
	
	/**
	 * 保存用户的用户组列表
	 * @param {Object} groupList
	 */
	setUserGroupList: function(groupList) {
		var groupListStr = JSON.stringify(groupList);
		plus.storage.setItem("groupList", groupListStr);
	},
	
	getUserGroupList: function() {
		var groupListStr = plus.storage.getItem("groupList");
		
		if (!this.isNotNull(groupListStr)) {
			return [];
		}
		
		return JSON.parse(groupListStr);
	},
	
	/**
	 * 根据用户id，从本地的缓存（联系人列表）中获取朋友的信息
	 * @param {Object} friendId
	 */
	getFriendFromContactList: function(friendId) {
		var contactListStr = plus.storage.getItem("contactList");
		
		// 判断contactListStr是否为空
		if (this.isNotNull(contactListStr)) {
			// 不为空，则把用户信息返回
			var contactList = JSON.parse(contactListStr);
			for (var i = 0 ; i < contactList.length ; i ++) {
				var friend = contactList[i];
				if (friend.friendUserId == friendId) {
					return friend;
					break;
				}
			}
		} else {
			// 如果为空，直接返回null
			return null;
		}
	},
	
	/**
	 * 从用户组中返回用户的信息
	 * @param {Object} groupId
	 * @param {Object} userId
	 */
	getUserFromUserGroupList: function(groupId, userId) {
		var groupListStr = plus.storage.getItem("groupList");
		
		// 判断contactListStr是否为空
		if (this.isNotNull(groupListStr)) {
			// 不为空，则把用户信息返回
			var groupList = JSON.parse(groupListStr);
			for (var i = 0 ; i < groupList.length ; i ++) {
				var group = groupList[i];
				if (group.id == groupId) {
					for (var j = 0; j < group.userList.length; j++) {
						var user = group.userList[i];
						if (group.userList[j].id == userId) {
							return group.userList[j];
						}
					}
				}
			}
		} else {
			// 如果为空，直接返回null
			return null;
		}
	},
	
	getUserGroupFromUserGroupList: function(groupId) {
		var groupListStr = plus.storage.getItem("groupList");
		// 判断contactListStr是否为空
		if (this.isNotNull(groupListStr)) {
			// 不为空，则把用户信息返回
			var groupList = JSON.parse(groupListStr);
			for (var i = 0; i < groupList.length; i++) {
				var group = groupList[i];
				if (group.id == groupId) {
					return group;
					break;
				}
			}
		} else {
			// 如果为空，直接返回null
			return null;
		}
	},
	
	
	/**
	 * 保存用户的聊天记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 * @param {Object} msg
	 * @param {Object} flag	判断本条消息是我发送的，还是朋友发送的，1:我  2:朋友
	 */
	saveUserChatHistory: function(myId, friendId, chatMsg, flag) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + friendId;
		chatMsg.msg = (chatMsg.type == "text" ? chatMsg.msg : app.imgServerUrl + chatMsg.msg);
		
		// 从本地缓存获取聊天记录是否存在
		var chatHistoryListStr = plus.storage.getItem(chatKey);
		var chatHistoryList = [];
		if (me.isNotNull(chatHistoryListStr)) {
			// 如果不为空
			chatHistoryList = JSON.parse(chatHistoryListStr);
		} 
		// 构建聊天记录对象
		var singleMsg = new me.ChatHistory(myId, friendId, chatMsg, flag);
		
		// 向list中追加msg对象
		chatHistoryList.push(singleMsg);
		
		plus.storage.setItem(chatKey, JSON.stringify(chatHistoryList));
	},
	
	/**
	 * 获取用户聊天记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 */
	getUserChatHistory: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + friendId;
		var chatHistoryListStr = plus.storage.getItem(chatKey);
		var chatHistoryList;
		if (me.isNotNull(chatHistoryListStr)) {
			// 如果不为空
			chatHistoryList = JSON.parse(chatHistoryListStr);
		} else {
			// 如果为空，赋一个空的list
			chatHistoryList = [];
		}
		return chatHistoryList;
	},
	
	// 删除我和朋友的聊天记录
	deleteUserChatHistory: function(myId, friendId) {
		var chatKey = "chat-" + myId + "-" + friendId;
		plus.storage.removeItem(chatKey);
	},
	
	/**
	 * 保存用户组的聊天记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 * @param {Object} msg
	 * @param {Object} flag	判断本条消息是我发送的，还是朋友发送的，1:我  2:用户组其他用户
	 */
	saveUserGroupChatHistory: function(myId, chatMsg, flag) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + chatMsg.receiverId;
		chatMsg.msg = (chatMsg.type == "text" ? chatMsg.msg : app.imgServerUrl + chatMsg.msg);
		// 从本地缓存获取聊天记录是否存在
		var chatHistoryListStr = plus.storage.getItem(chatKey);
		var chatHistoryList = [];
		if (me.isNotNull(chatHistoryListStr)) {
			// 如果不为空
			chatHistoryList = JSON.parse(chatHistoryListStr);
		} 
		// 构建聊天记录对象
		var singleMsg = new me.ChatHistory(null, null, chatMsg, flag);
		
		// 向list中追加msg对象
		chatHistoryList.push(singleMsg);
		
		plus.storage.setItem(chatKey, JSON.stringify(chatHistoryList));
	},
	
	/**
	 * 获取用户组聊天记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 */
	getUserGroupChatHistory: function(myId, groupId) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + groupId;
		var chatHistoryListStr = plus.storage.getItem(chatKey);
		var chatHistoryList;
		if (me.isNotNull(chatHistoryListStr)) {
			// 如果不为空
			chatHistoryList = JSON.parse(chatHistoryListStr);
		} else {
			// 如果为空，赋一个空的list
			chatHistoryList = [];
		}
		return chatHistoryList;
	},
	
	
	/**
	 * 聊天记录的快照，仅仅保存每次和朋友聊天的最后一条消息
	 * @param {Object} myId
	 * @param {Object} friendId type 0 为好友id 1为用户组id
	 * @param {Object} msg
	 * @param {Object} isRead
	 * @param {type} 0 1
	 */
	saveUserChatSnapshot: function(myId, friendId, msg, readCount, type) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		
		// 从本地缓存获取聊天快照的list
		var chatSnapshotListStr = plus.storage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			// 如果不为空
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			// 循环快照list，并且判断每个元素是否包含（匹配）friendId，如果匹配，则删除
			for (var i = 0; i < chatSnapshotList.length; i++) {
				if (chatSnapshotList[i].friendId == friendId && chatSnapshotList[i].type == type) {
					// 删除已经存在的friendId所对应的快照对象
					chatSnapshotList.splice(i, 1);
					break;
				}
			}
		} else {
			// 如果为空，赋一个空的list
			chatSnapshotList = [];
		}
		
		// 构建聊天快照对象
		var singleMsg = new me.ChatSnapshot(myId, friendId, msg, readCount, type);
		
		// 向list中追加快照对象
		chatSnapshotList.unshift(singleMsg);
		
		plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList));
	},
	
	getUserChatSnapshot: function(myId, friendId, type) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		// 从本地缓存获取聊天快照的list
		var chatSnapshotListStr = plus.storage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			// 如果不为空
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			// 循环快照list，并且判断每个元素是否包含（匹配）friendId，如果匹配，则删除
			for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
				if (chatSnapshotList[i].friendId == friendId && chatSnapshotList[i].type == type) {
					return chatSnapshotList[i];
				}
			}
		}
		return null;
	},
	
	/**
	 * 获取用户快照记录列表
	 */
	getUserChatSnapshotList: function(myId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		// 从本地缓存获取聊天快照的list
		var chatSnapshotListStr = plus.storage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			// 如果不为空
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
		} else {
			// 如果为空，赋一个空的list
			chatSnapshotList = [];
		}
		
		return chatSnapshotList;
	},
	
	/**
	 * 删除本地的聊天快照记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 */
	deleteUserChatSnapshot: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		
		// 从本地缓存获取聊天快照的list
		var chatSnapshotListStr = plus.storage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			// 如果不为空
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			// 循环快照list，并且判断每个元素是否包含（匹配）friendId，如果匹配，则删除
			for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
				if (chatSnapshotList[i].friendId == friendId) {
					// 删除已经存在的friendId所对应的快照对象
					chatSnapshotList.splice(i, 1);
					break;
				}
			}
		} else {
			// 如果为空，不做处理
			return;
		}
		
		plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList));
	},
	
	/**
	 * 标记未读消息为已读状态
	 * @param {Object} myId
	 * @param {Object} friendId
	 * @param {type} 0 单聊 1群聊
	 */
	readUserChatSnapshot: function(myId, friendId, type) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		// 从本地缓存获取聊天快照的list
		var chatSnapshotListStr = plus.storage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			// 如果不为空
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			// 循环这个list，判断是否存在好友，比对friendId，
			// 如果有，在list中的原有位置删除该 快照 对象，然后重新放入一个标记已读的快照对象
			for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
				var item = chatSnapshotList[i];
				if (item.friendId == friendId && item.type == type) {
					item.readCount = 0;		// 未读消息数
					chatSnapshotList.splice(i, 1, item);	// 替换原有的快照
					break;
				}
			}
			// 替换原有的快照列表
			plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList));
		} else {
			// 如果为空
			return;
		}
	},

	/**
	 * 和后端的枚举对应
	 */
	CONNECT : 1, 	// 第一次(或重连)初始化连接
	CHAT : 2, 		// 聊天消息
	SIGNED : 3,  // 消息签收
	KEEPALIVE : 4, 	// 客户端保持心跳
	PULL_FRIEND : 5,  // 重新拉取好友
	GROUP_CHAT : 6, // 群组聊天消息
	GROUP_SIGNED : 7, // 群消息签收
	
	/**
	 * 和后端的 ChatMsg 聊天模型对象保持一致
	 * @param {Object} senderId
	 * @param {Object} receiverId
	 * @param {Object} msg
	 * @param {Object} msgId
	 */
	ChatMsg: function(senderId, receiverId, msg, type, msgId) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.msg = msg;
		this.type = type;
		this.msgId = msgId;
	},
	
	/**
	 * 构建消息 DataContent 模型对象
	 * @param {Object} action
	 * @param {Object} chatMsg
	 * @param {Object} extand
	 */
	DataContent: function(action, chatMsg, extend) {
		this.action = action;
		this.chatMsg = chatMsg;
		this.extend = extend;
	},
	
	/**
	 * 单个聊天记录的对象
	 * @param {Object} myId
	 * @param {Object} friendId
	 * @param {Object} msg
	 * @param {Object} flag
	 */
	ChatHistory: function(myId, friendId, chatMsg, flag) {
		this.myId = myId;
		this.friendId = friendId;
		this.chatMsg = chatMsg;
		this.flag = flag;
	},
	
	/**
	 * 快照对象
	 * @param {Object} myId
	 * @param {Object} friendId
	 * @param {Object} msg
	 * @param {Object} isRead	用于判断消息是否已读还是未读
	 * @param {type} 用户判断聊天快照是单聊
	 */
	ChatSnapshot: function(myId, friendId, msg, readCount, type) {
		this.myId = myId;
		this.friendId = friendId;
		this.msg = msg;
		this.readCount = readCount;
		this.type = type;
	}
	
}
