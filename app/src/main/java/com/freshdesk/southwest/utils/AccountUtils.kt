package com.freshdesk.southwest.utils

import com.freshworks.sdk.freshdesk.data.SDKConfig

object AccountUtils {

    val configEU = SDKConfig(
        token = "your_eu_token",
        host = "https://eu.freshdesk.com",
        sdkID = "your_eu_sdk_id",
    )

    val configAU = SDKConfig(
        token = "your_au_token",
        host = "https://au.freshdesk.com",
        sdkID = "your_au_sdk_id",
    )

    /*  val configFreshPori = SDKConfig(
          token = "01K04ANY6VDJRK6P2KA8M671KT",
          host = "https://sanjaykrishnan.freshpo.com",
          sdkID = "TODO replace with your sdk id",
          widgetID = "01K04AP072Y2A93DW7WS8C1J71", //Todo comes from remote config
      )*/

    val configFreshPori = SDKConfig(
        token = "01JT8A3RARGKBQ1VJ7QE1HQ05P",
        host = "https://fandfbranchdemotest.freshpo.com",
        sdkID = "TODO replace with your sdk id",
//        jwtAuthToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RAdGV
        //        zdC5jb20iLCJjb250YWN0Ijp7Im5hbWUiOiJUZXN0In0sInRpY2tldCI6eyJ0eXBlIjoi
        //        SW5jaWRlbnQifX0.6lfrpV9Y4eXhGQajAO64TGcgbDDCuOJGFCL552Q2-wQ"
    )

    val staging = SDKConfig(
        token = "01K88N8V21ST3TKEJJJ5HQS7HY",
        host = "https://susilkumarudhayakumar6532.freshpo.com",
        locale = "en",
        sdkID = "01K88V0WB2XG0PBSPEVMPKT4MR"
    )

    val account360 = SDKConfig(
        token = "01K6WWVE9TX6Z9KW1C1BZHC6RK",
        host = "https://mobiletesting-help.freshpo.com",
        locale = "en",
        sdkID = "01K6YY5904DA2A3MNN7J87HAHJ",
    )

//    val configFreshPori = SDKConfig(
//        token = "01JZ5JYECX92ATBVDH7MA42K8D",
//        host = "https://fandfbranchdemotest.freshpo.com/",
//        sdkID = "TODO replace with your sdk id",
//        widgetID = "01K4MJ9DVYF7ZTG7DGXFA438PV", // Todo comes from remote config
//    )
}