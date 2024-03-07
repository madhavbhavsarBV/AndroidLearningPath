package com.base.hilt.domain.model

data class NotificationsListData (
     val uuid: String?= null,
     val title: String?= null,
     val content: String?= null,
     val read: Int?= null,
     val created_at: String?= null,
     val image: String?= null,
     val action_uuid: String?= null,
     val type: String?= null,
     val ago: String?= null,
     val push_type: String?= null,
)