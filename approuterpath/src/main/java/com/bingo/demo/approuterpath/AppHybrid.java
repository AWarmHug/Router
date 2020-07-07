package com.bingo.demo.approuterpath;

import com.bingo.router.Request;
import com.bingo.router.Router;
import com.bingo.router.annotations.PathClass;

import java.io.Serializable;

public interface AppHybrid {
    @PathClass("/apphybrid/web")
    public static interface Web{

        public static class WebInfo implements Serializable {
            private String title;
            private String url;

            public String getTitle() {
                return title;
            }

            public String getUrl() {
                return url;
            }

            public static WebInfo justUrl(String url) {
                WebInfo webInfo = new WebInfo();
                webInfo.url = url;
                return webInfo;
            }
        }
    }

    @PathClass("/apphybrid/empty")
    public interface Empty {

    }
}
