package com.ojw.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 */
public class HttpClientUtil {

    /**
     * 创建忽略证书的Client
     */
    private static CloseableHttpClient getClient() throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        // 忽略证书
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new PlainConnectionSocketFactory())//
                .register("https", sslsf).build();//
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(9999);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm).build();
        return httpclient;
    }

    /**
     * 发送 GET 请求（HTTP）
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String,String> params) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = null;
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClientUtil.getClient();
//            httpClient = HttpClients.createDefault();
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if(params!=null) {
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            String ip=randIP();
            httpGet.setHeader("X-Forwarded-For",ip);
            httpGet.setHeader("Proxy-Client-IP",ip);
            httpGet.setHeader("WL-Proxy-Client-IP",ip);
            httpGet.setHeader("remoteAddr",ip);

            //执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if(response.getStatusLine().getStatusCode()==200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response!=null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

	public static String doGet(String url) {
		return doGet(url, null);
	}

    /**
     * 发送 dopost 请求（HTTP）
     * @param url
     * @param params
     * @return
     */
	public static String doPost(String url, Map<String,String> params) {
	    //创建Httpclient对象
        CloseableHttpClient httpClient = null;
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClientUtil.getClient();
            //创建httppost请求
            HttpPost httpPost = new HttpPost(url);
            String ip=randIP();
            httpPost.setHeader("X-Forwarded-For",ip);
            httpPost.setHeader("Proxy-Client-IP",ip);
            httpPost.setHeader("WL-Proxy-Client-IP",ip);
            httpPost.setHeader("remoteAddr",ip);
            //创建参数列表
            if(params!=null) {
                List<NameValuePair> pamraList = new ArrayList<NameValuePair>();
                for (String key : params.keySet()) {
                    pamraList.add(new BasicNameValuePair(key,params.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pamraList, "UTF-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString =  EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response != null) {
                    response.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

	public static String doPost(String url) {
		return doPost(url, null);
	}

    /**
     * 发送 post 请求（HTTP） json
     * @param url
     * @param json
     * @return
     */
	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String resultString = "";
		try {
            httpClient = HttpClientUtil.getClient();
            //创建httpPost请求
            HttpPost httpPost = new HttpPost(url);
            String ip=randIP();
            httpPost.setHeader("X-Forwarded-For",ip);
            httpPost.setHeader("Proxy-Client-IP",ip);
            httpPost.setHeader("WL-Proxy-Client-IP",ip);
            httpPost.setHeader("remoteAddr",ip);
            //创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            //执行请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        }catch (Exception e) {
		    e.printStackTrace();
        } finally {
		    try{
                if(response!=null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
	}


//    public static void main(String[] args) throws Exception {
//	    String ybduUrl = "https://m.ybdu.com";
//	    String url = ybduUrl+"/book1/0/1/";//212
//        //获取正文
//        String txt = myCms.substring(myCms.indexOf("id=\"txt\">"),myCms.indexOf("</div>"));
//        txt = strText(txt);
//        System.out.println(txt);
//        //获取标题
//        String title = myCms.substring(myCms.indexOf("nr_title\">"),myCms.indexOf("</h1>"));
//        title = title.replaceAll( "[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , "");
//        title = title.replaceAll("[0-9a-zA-Z]","");
//
//        //获取下一页地址
//        String next = myCms.substring(myCms.indexOf("pt_next"));
//        String nextPage = next.substring(next.indexOf("href")+6,next.indexOf(">")-1);
//        System.out.println(title);
//        System.out.println(nextPage);
//        writeString(title);
////        System.out.println(myCms);
//        getAgentData("/xiaoshuo/13/13869/3981510.html",1,696,"无限道武者路");
//
//
//        getAgentData("/xiaoshuo/23/23407/10372716.html",1,1548,"末世全能剑神TXT");
//    }

    public static String getAgentData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dataStr = "";
        Date date=new Date();
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(date);
        currentTime.add(Calendar.YEAR,1);
        dataStr = sdf.format(currentTime.getTime());
        return dataStr;
    }

    /**
     * 递归抓取小说
     * @param url
     */
    public static void getAgentData(String url,int page,int pageSize,String name) throws InterruptedException {
        if (page>pageSize){
            return;
        }
//        Thread.sleep(5000);
        String ybduUrl = "https://m.ybdu.com";
        String ybduHtml ="";
        while (""==ybduHtml){
            try {
                ybduHtml = HttpClientUtil.doGet(ybduUrl+url);
            }catch (Exception e){
                ybduHtml ="";
            }
        }


        System.out.println(ybduUrl+url+"    page:"+page);
        //初步处理
        ybduHtml = ybduHtml.substring(ybduHtml.indexOf("nr_title"),ybduHtml.indexOf("</body>"));
        //获取标题
        String title = ybduHtml.substring(ybduHtml.indexOf("nr_title\">"),ybduHtml.indexOf("</h1>"));
        title = "第"+page+"章,"+strText(title);
        System.out.println(title+"    page:"+page);
        writeString(title+"\r\n",name);
        //获取正文
        String txt = ybduHtml.substring(ybduHtml.indexOf("id=\"txt\">"),ybduHtml.indexOf("</div>"));
        txt = strText(txt);
        writeString(txt+"\r\n",name);
        //获取下一页地址
        String next = ybduHtml.substring(ybduHtml.indexOf("pt_next"));
        String nextPage = next.substring(next.indexOf("href")+6,next.indexOf(">")-1);
        page++;
        getAgentData(nextPage,page,pageSize,name);
    }

    /**
     * 正则剔除符号和英文
     * [\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]
     * @return
     */
    public static String strText(String text){
        text = text.replaceAll( "[+~$`^=|<>～｀＄＾＋＝｜＜＞￥×/&;a-zA-Z]" , "");
//        text = text.replaceAll("[0-9]","");
        return text;
    }

    /**
     * 向文件中写入一个字符串
     * windows \r\n  是换行
     */
    private static void writeString(String str,String name) {
        FileWriter fw=null;
        try {
            //在工程的根目录下创建一个FileWriter对象，如果该目录下已有同名文件，旧的文件将被覆盖
            fw=new FileWriter("E:\\text\\"+name+".txt",true);
            //要被写入的字符串
            fw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(fw!=null){
                    //关闭数据流
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 代理设置
     * @param url
     * @return
     * @throws Exception
     */
    public static String getHttpClients(String url) throws Exception {
        HttpClientBuilder build = HttpClients.custom();
        HttpHost proxy = new HttpHost("202.107.233.85", 8080);
        CloseableHttpClient client = build.setProxy(proxy).build();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    /**
     * 随机ip生成
     * @return
     */
    public static String randIP() {
        Random random = new Random(System.currentTimeMillis());
        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1)
                + "." + (random.nextInt(255) + 1) + "."
                + (random.nextInt(255) + 1);
    }

}
