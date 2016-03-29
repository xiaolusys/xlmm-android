package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMChooselistBean {


  /**
   * count : 184
   * next : http://api.xiaolumeimei.com/rest/v1/products/my_choice_pro?page=2
   * previous : null
   * results : [{"agent_price":45,"in_customer_shop":0,"name":"韩版紧身五分袖T恤/酒红色","std_sale_price":198,"remain_num":200,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"3804人在卖","pic_path":"http://image.xiaolu.so/MG_1458971041457头图背景1.png","sale_num":3804,"id":37021,"rebet_amount":0},{"agent_price":49.9,"in_customer_shop":0,"name":"韩版针织鱼尾裙/红色","std_sale_price":110,"remain_num":10,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"202人在卖","pic_path":"http://image.xiaolu.so/MG_1458960192538头图背景4.png","sale_num":202,"id":37017,"rebet_amount":0},{"agent_price":89.9,"in_customer_shop":0,"name":"灯笼袖中长款开衫/粉色","std_sale_price":226,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"958人在卖","pic_path":"http://image.xiaolu.so/MG_1459153137757头图背景2.png","sale_num":958,"id":37013,"rebet_amount":0},{"agent_price":39.9,"in_customer_shop":0,"name":"可爱条纹高弹打底裙/黑白条纹","std_sale_price":99,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"963人在卖","pic_path":"http://image.xiaolu.so/MG_1458986052062头图背景.png","sale_num":963,"id":37012,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"镂空针织衫+雪纺衬衫两件套/藏青色","std_sale_price":116,"remain_num":10,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"191人在卖","pic_path":"http://image.xiaolu.so/MG_1458958497950头图背景4.png","sale_num":191,"id":37008,"rebet_amount":0},{"agent_price":49.9,"in_customer_shop":0,"name":"糖果色针织开衫/玫红色","std_sale_price":140,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"952人在卖","pic_path":"http://image.xiaolu.so/MG_1459153992668头图背景1.png","sale_num":952,"id":37004,"rebet_amount":0},{"agent_price":119.9,"in_customer_shop":0,"name":"蕾丝拼接中长款打底裙/灰色","std_sale_price":178,"remain_num":10,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"207人在卖","pic_path":"http://image.xiaolu.so/MG_1458957461462头图背景3.png","sale_num":207,"id":36999,"rebet_amount":0},{"agent_price":89.9,"in_customer_shop":0,"name":"水洗百搭背带裤/图片色","std_sale_price":399,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"952人在卖","pic_path":"http://image.xiaolu.so/MG_1458986016479头图背景.png","sale_num":952,"id":36998,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"韩版宽松假两件蝙蝠衫/玫红色","std_sale_price":116,"remain_num":10,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"196人在卖","pic_path":"http://image.xiaolu.so/MG_1458956800588头图背景3.png","sale_num":196,"id":36994,"rebet_amount":0},{"agent_price":79.9,"in_customer_shop":0,"name":"条纹针织外套+背心裙两件套/粉色","std_sale_price":158,"remain_num":10,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"194人在卖","pic_path":"http://image.xiaolu.so/MG_1458957846195主图.png","sale_num":194,"id":36990,"rebet_amount":0},{"agent_price":29.9,"in_customer_shop":0,"name":"可爱字母休闲开衫/红色","std_sale_price":99,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"960人在卖","pic_path":"http://image.xiaolu.so/MG_1458985938425头图背景1.png","sale_num":960,"id":36987,"rebet_amount":0},{"agent_price":39.9,"in_customer_shop":0,"name":"条纹吊带背心包臀裙/图片色","std_sale_price":70,"remain_num":10,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"198人在卖","pic_path":"http://image.xiaolu.so/MG_1458907680921头图.png","sale_num":198,"id":36986,"rebet_amount":0},{"agent_price":59.9,"in_customer_shop":0,"name":"卡通长袖印花上衣/白色","std_sale_price":299,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"960人在卖","pic_path":"http://image.xiaolu.so/MG_1458985885734头图背景1.png","sale_num":960,"id":36985,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"个性翻领百搭外套/米色","std_sale_price":98,"remain_num":10,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"204人在卖","pic_path":"http://image.xiaolu.so/MG_1458901294626主图4.png","sale_num":204,"id":36978,"rebet_amount":0},{"agent_price":109.9,"in_customer_shop":0,"name":"宽松大码针织外套/粉色","std_sale_price":198,"remain_num":10,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"198人在卖","pic_path":"http://image.xiaolu.so/MG_1458897497469主图1.png","sale_num":198,"id":36975,"rebet_amount":0},{"agent_price":59.9,"in_customer_shop":0,"name":"宽松V领针织开衫/米色","std_sale_price":96,"remain_num":10,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"192人在卖","pic_path":"http://image.xiaolu.so/MG_1458894339006主图2.png","sale_num":192,"id":36972,"rebet_amount":0},{"agent_price":99.9,"in_customer_shop":0,"name":"时尚字母印花运动套/红色","std_sale_price":499,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"963人在卖","pic_path":"http://image.xiaolu.so/MG_1458985823521头图背景3.png","sale_num":963,"id":36969,"rebet_amount":0},{"agent_price":59.9,"in_customer_shop":0,"name":"韩版印花背心裙套装/藏蓝色","std_sale_price":199,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"955人在卖","pic_path":"http://image.xiaolu.so/MG_1458985777550头图背景.png","sale_num":955,"id":36968,"rebet_amount":0},{"agent_price":39.9,"in_customer_shop":0,"name":"卡通米奇印花上衣/灰色","std_sale_price":99,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"955人在卖","pic_path":"http://image.xiaolu.so/MG_1458985733482头图背景.png","sale_num":955,"id":36967,"rebet_amount":0},{"agent_price":29.9,"in_customer_shop":0,"name":"新款纯色紧身T恤/红色","std_sale_price":168,"remain_num":200,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"3808人在卖","pic_path":"http://image.xiaolu.so/MG_1458973436209头图背景2.png","sale_num":3808,"id":36962,"rebet_amount":0},{"agent_price":49.9,"in_customer_shop":0,"name":"韩版中袖紧身T恤/酒红色","std_sale_price":198,"remain_num":200,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"3817人在卖","pic_path":"http://image.xiaolu.so/MG_1458986906731头图背景.png","sale_num":3817,"id":36958,"rebet_amount":0},{"agent_price":89.9,"in_customer_shop":0,"name":"多彩沙滩防晒衫/蓝色海洋","std_sale_price":198,"remain_num":250,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"4761人在卖","pic_path":"http://image.xiaolu.so/MG_1458888442086头图3.png","sale_num":4761,"id":36954,"rebet_amount":0},{"agent_price":29.9,"in_customer_shop":0,"name":"卡通图纹T恤/绿色","std_sale_price":59,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"960人在卖","pic_path":"http://image.xiaolu.so/MG_1458897571534头图背景1.png","sale_num":960,"id":36952,"rebet_amount":0},{"agent_price":39.9,"in_customer_shop":0,"name":"蝴蝶结蕾丝裙/粉色","std_sale_price":139,"remain_num":25,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"479人在卖","pic_path":"http://image.xiaolu.so/MG_1458897381882头图背景22.png","sale_num":479,"id":36948,"rebet_amount":0},{"agent_price":29.9,"in_customer_shop":0,"name":"韩版水墨花V领T恤/白底灰色花","std_sale_price":59,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"957人在卖","pic_path":"http://image.xiaolu.so/MG_1458897640131头图背景11.png","sale_num":957,"id":36946,"rebet_amount":0},{"agent_price":29.9,"in_customer_shop":0,"name":"韩版格纹袖T恤/灰黑","std_sale_price":59,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"960人在卖","pic_path":"http://image.xiaolu.so/MG_1458898174586头图背景1.png","sale_num":960,"id":36944,"rebet_amount":0},{"agent_price":49.9,"in_customer_shop":0,"name":"格纹花边裙/粉色","std_sale_price":159,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"968人在卖","pic_path":"http://image.xiaolu.so/MG_1458898587859头图背景2.png","sale_num":968,"id":36942,"rebet_amount":0},{"agent_price":59.9,"in_customer_shop":0,"name":"花朵高腰衣裙/黄色","std_sale_price":159,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"961人在卖","pic_path":"http://image.xiaolu.so/MG_1458898520002头图背景2.png","sale_num":961,"id":36940,"rebet_amount":0},{"agent_price":19.9,"in_customer_shop":0,"name":"蕾丝小熊五分裤/黄色","std_sale_price":99,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"959人在卖","pic_path":"http://image.xiaolu.so/MG_1458898709176头图背景2.png","sale_num":959,"id":36938,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"纯棉衬衫+背带裙套装/红格裙","std_sale_price":178,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"964人在卖","pic_path":"http://image.xiaolu.so/MG_1458892868711纯棉衬衫背带裙套装03.png","sale_num":964,"id":36935,"rebet_amount":0},{"agent_price":39.9,"in_customer_shop":0,"name":"春款韩版打底衫/粉色","std_sale_price":99,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"963人在卖","pic_path":"http://image.xiaolu.so/MG_1458898460969头图背景2.png","sale_num":963,"id":36932,"rebet_amount":0},{"agent_price":99.9,"in_customer_shop":0,"name":"潮范时尚家庭装/妈妈款","std_sale_price":172,"remain_num":30,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"588人在卖","pic_path":"http://image.xiaolu.so/MG_1458892899139潮范时尚家庭装03.png","sale_num":588,"id":36931,"rebet_amount":0},{"agent_price":99.9,"in_customer_shop":0,"name":"情侣全棉印花夹克/卡其色","std_sale_price":238,"remain_num":120,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"2297人在卖","pic_path":"http://image.xiaolu.so/MG_1458903284292情侣全棉印花夹克04.png","sale_num":2297,"id":36928,"rebet_amount":0},{"agent_price":169.9,"in_customer_shop":0,"name":"时尚雪纺上衣+鱼尾背带裙套装/图片色","std_sale_price":299,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1534人在卖","pic_path":"http://image.xiaolu.so/MG_1458906579321时尚雪纺上衣鱼尾背带裙套装02.png","sale_num":1534,"id":36927,"rebet_amount":0},{"agent_price":158,"in_customer_shop":0,"name":"气质V领连衣裙/红色","std_sale_price":299,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1527人在卖","pic_path":"http://image.xiaolu.so/MG_1458903486042气质V领连衣裙02.png","sale_num":1527,"id":36923,"rebet_amount":0},{"agent_price":39.9,"in_customer_shop":0,"name":"卡通小兔印花长裤/粉色","std_sale_price":79,"remain_num":25,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"493人在卖","pic_path":"http://image.xiaolu.so/MG_1458881877476头图背景1.png","sale_num":493,"id":36921,"rebet_amount":0},{"agent_price":39.9,"in_customer_shop":0,"name":"新款简约百搭衬衫/玫红色","std_sale_price":99,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1529人在卖","pic_path":"http://image.xiaolu.so/MG_1458893798398头图背景2.png","sale_num":1529,"id":36917,"rebet_amount":0},{"agent_price":138,"in_customer_shop":0,"name":"时尚图纹印花两件套/蓝色","std_sale_price":258,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1532人在卖","pic_path":"http://image.xiaolu.so/MG_1458903456323蕾丝创意两件套2.png","sale_num":1532,"id":36915,"rebet_amount":0},{"agent_price":39,"in_customer_shop":0,"name":"韩范休闲宽松衬衫/图片色","std_sale_price":99,"remain_num":250,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"4762人在卖","pic_path":"http://image.xiaolu.so/MG_1458893672762头图背景1.png","sale_num":4762,"id":36914,"rebet_amount":0},{"agent_price":29.9,"in_customer_shop":0,"name":"蝴蝶结钉珠蕾丝打底裤/藏蓝色","std_sale_price":79,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"960人在卖","pic_path":"http://image.xiaolu.so/MG_1458881789931头图背景3.png","sale_num":960,"id":36910,"rebet_amount":0},{"agent_price":39,"in_customer_shop":0,"name":"韩版大码休闲衬衣/图片色","std_sale_price":158,"remain_num":300,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"5707人在卖","pic_path":"http://image.xiaolu.so/MG_1458893285190头图背景.png","sale_num":5707,"id":36909,"rebet_amount":0},{"agent_price":69,"in_customer_shop":0,"name":"修身纯棉牛仔衬衫/浅蓝色","std_sale_price":99,"remain_num":100,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1914人在卖","pic_path":"http://image.xiaolu.so/MG_1458906548277修身纯棉牛仔衬衫04.png","sale_num":1914,"id":36907,"rebet_amount":0},{"agent_price":68.9,"in_customer_shop":0,"name":"时尚情侣装外套/红色","std_sale_price":238,"remain_num":120,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"2283人在卖","pic_path":"http://image.xiaolu.so/MG_1458903210631时尚情侣装外套06.png","sale_num":2283,"id":36902,"rebet_amount":0},{"agent_price":139.9,"in_customer_shop":0,"name":"修身蕾丝小香风连衣裙/红色","std_sale_price":299,"remain_num":40,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"774人在卖","pic_path":"http://image.xiaolu.so/MG_1458895326631头图背景1.png","sale_num":774,"id":36900,"rebet_amount":0},{"agent_price":79.9,"in_customer_shop":0,"name":"休闲印花套装/蓝色","std_sale_price":298,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1530人在卖","pic_path":"http://image.xiaolu.so/MG_1458903136659休闲印花套装03.png","sale_num":1530,"id":36898,"rebet_amount":0},{"agent_price":149.9,"in_customer_shop":0,"name":"新款假两件开衫/粉色","std_sale_price":299,"remain_num":20,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"395人在卖","pic_path":"http://image.xiaolu.so/MG_1458894558193头图背景1.png","sale_num":395,"id":36893,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"韩版字母印花牛仔裤/图片色","std_sale_price":299,"remain_num":60,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1155人在卖","pic_path":"http://image.xiaolu.so/MG_1458892962854韩版字母印花牛仔裤02.png","sale_num":1155,"id":36892,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"时尚拼色冲锋衣/粉色","std_sale_price":199,"remain_num":60,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1148人在卖","pic_path":"http://image.xiaolu.so/MG_145889283642802.png","sale_num":1148,"id":36889,"rebet_amount":0},{"agent_price":59.9,"in_customer_shop":0,"name":"复古印花连衣裙/白色","std_sale_price":198,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1520人在卖","pic_path":"http://image.xiaolu.so/MG_1458895824546头图背景1.png","sale_num":1520,"id":36888,"rebet_amount":0},{"agent_price":49.9,"in_customer_shop":0,"name":"抽象印花大码连衣裙/图片色","std_sale_price":198,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1525人在卖","pic_path":"http://image.xiaolu.so/MG_1458895693723头图背景1.png","sale_num":1525,"id":36887,"rebet_amount":0},{"agent_price":99.9,"in_customer_shop":0,"name":"镂空修身针织衫/玫红色","std_sale_price":199,"remain_num":30,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"580人在卖","pic_path":"http://image.xiaolu.so/MG_1458894808175头图背景1.png","sale_num":580,"id":36883,"rebet_amount":0},{"agent_price":49.9,"in_customer_shop":0,"name":"时尚拼接印花防晒衫/黄色","std_sale_price":199,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"951人在卖","pic_path":"http://image.xiaolu.so/MG_1458892763490时尚拼接印花防晒衫03.png","sale_num":951,"id":36881,"rebet_amount":0},{"agent_price":149.9,"in_customer_shop":0,"name":"气质蕾丝拼接连衣裙/红色","std_sale_price":269,"remain_num":40,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"761人在卖","pic_path":"http://image.xiaolu.so/MG_1458895187549头图背景1.png","sale_num":761,"id":36879,"rebet_amount":0},{"agent_price":49.9,"in_customer_shop":0,"name":"可爱圆领印花连衣裙/红色","std_sale_price":99,"remain_num":60,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1155人在卖","pic_path":"http://image.xiaolu.so/MG_1458898835629头图背景2.png","sale_num":1155,"id":36877,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"韩版宽松长卫衣/粉色","std_sale_price":158,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1529人在卖","pic_path":"http://image.xiaolu.so/MG_1458895540810头图背景1.png","sale_num":1529,"id":36873,"rebet_amount":0},{"agent_price":79.9,"in_customer_shop":0,"name":"卡通小怪兽印花套装/图片色","std_sale_price":299,"remain_num":40,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"766人在卖","pic_path":"http://image.xiaolu.so/MG_1458881732600头图背景.png","sale_num":766,"id":36872,"rebet_amount":0},{"agent_price":119.9,"in_customer_shop":0,"name":"韩版修身条纹西装/灰色条纹","std_sale_price":298,"remain_num":150,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"2860人在卖","pic_path":"http://image.xiaolu.so/MG_1458816641328头图32.png","sale_num":2860,"id":36870,"rebet_amount":0},{"agent_price":259.9,"in_customer_shop":0,"name":"气质修身风衣/粉色","std_sale_price":417,"remain_num":6,"shop_product_num":4,"rebet_amount_des":"佣 ￥12","sale_num_des":"118人在卖","pic_path":"http://image.xiaolu.so/MG_1458906480918气质修身风衣01.png","sale_num":118,"id":36869,"rebet_amount":12},{"agent_price":53.9,"in_customer_shop":0,"name":"修身印字母打底衫/白色","std_sale_price":138,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1538人在卖","pic_path":"http://image.xiaolu.so/MG_1458892870521头图背景1.png","sale_num":1538,"id":36864,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"气质镂空蕾丝蓬蓬裙/图片色","std_sale_price":199,"remain_num":60,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1157人在卖","pic_path":"http://image.xiaolu.so/MG_1458897499490头图背景.png","sale_num":1157,"id":36863,"rebet_amount":0},{"agent_price":53.9,"in_customer_shop":0,"name":"韩版长袖T恤/白色","std_sale_price":128,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1523人在卖","pic_path":"http://image.xiaolu.so/MG_1458892620820头图背景1.png","sale_num":1523,"id":36858,"rebet_amount":0},{"agent_price":139.9,"in_customer_shop":0,"name":"时尚蕾丝连衣裙/红色","std_sale_price":298,"remain_num":150,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"2863人在卖","pic_path":"http://image.xiaolu.so/MG_1458821543375头图4.png","sale_num":2863,"id":36855,"rebet_amount":0},{"agent_price":169,"in_customer_shop":0,"name":"修身雪纺印花裙/粉色","std_sale_price":264,"remain_num":15,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"296人在卖","pic_path":"http://image.xiaolu.so/MG_1458906504343修身雪纺印花裙04.png","sale_num":296,"id":36853,"rebet_amount":0},{"agent_price":149.9,"in_customer_shop":0,"name":"水墨印花两件套长裙/图片色","std_sale_price":298,"remain_num":250,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"4764人在卖","pic_path":"http://image.xiaolu.so/MG_1458875717537头图.png","sale_num":4764,"id":36852,"rebet_amount":0},{"agent_price":79.9,"in_customer_shop":0,"name":"韩版印花棒球服/白色花朵","std_sale_price":198,"remain_num":60,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1152人在卖","pic_path":"http://image.xiaolu.so/MG_1458891822120头图背景1.png","sale_num":1152,"id":36849,"rebet_amount":0},{"agent_price":119.9,"in_customer_shop":0,"name":"气质蕾丝雪纺衫两件套/白色","std_sale_price":210,"remain_num":25,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"490人在卖","pic_path":"http://image.xiaolu.so/MG_1458903433309气质蕾丝雪纺衫两件套02.png","sale_num":490,"id":36848,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"针织拼接长卫衣/粉色","std_sale_price":165,"remain_num":80,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1528人在卖","pic_path":"http://image.xiaolu.so/MG_1458891142301头图背景1.png","sale_num":1528,"id":36845,"rebet_amount":0},{"agent_price":149.9,"in_customer_shop":0,"name":"韩版时尚牛仔外套/图片色","std_sale_price":439,"remain_num":200,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"3818人在卖","pic_path":"http://image.xiaolu.so/MG_1458889756718头图背景1.png","sale_num":3818,"id":36844,"rebet_amount":0},{"agent_price":89.9,"in_customer_shop":0,"name":"帅气拉链PU夹克/红色","std_sale_price":399,"remain_num":60,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1152人在卖","pic_path":"http://image.xiaolu.so/MG_1458898763067头图背景2.png","sale_num":1152,"id":36842,"rebet_amount":0},{"agent_price":149.9,"in_customer_shop":0,"name":"小清新连衣裙两件套/紫裙白镂空外套","std_sale_price":298,"remain_num":250,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"4757人在卖","pic_path":"http://image.xiaolu.so/MG_1458890539230头图背景1.png","sale_num":4757,"id":36840,"rebet_amount":0},{"agent_price":79.9,"in_customer_shop":0,"name":"可爱大白印花两件套/蓝色","std_sale_price":299,"remain_num":40,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"773人在卖","pic_path":"http://image.xiaolu.so/MG_1458881680322头图背景1.png","sale_num":773,"id":36838,"rebet_amount":0},{"agent_price":65.9,"in_customer_shop":0,"name":"显瘦破洞九分牛仔裤/图片色","std_sale_price":298,"remain_num":240,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"4560人在卖","pic_path":"http://image.xiaolu.so/MG_1458903316832潮流显瘦破洞九分牛仔裤04.png","sale_num":4560,"id":36837,"rebet_amount":0},{"agent_price":64.9,"in_customer_shop":0,"name":"修身显瘦九分哈伦裤/黑色","std_sale_price":298,"remain_num":150,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"2852人在卖","pic_path":"http://image.xiaolu.so/MG_1458903341176修身显瘦九分哈伦裤02.png","sale_num":2852,"id":36836,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"可爱超级玛丽背带套装/黄色","std_sale_price":199,"remain_num":40,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"768人在卖","pic_path":"http://image.xiaolu.so/MG_1458881589494头图背景1.png","sale_num":768,"id":36834,"rebet_amount":0},{"agent_price":59.9,"in_customer_shop":0,"name":"英伦格子中长款衬衫/红格","std_sale_price":199,"remain_num":60,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1153人在卖","pic_path":"http://image.xiaolu.so/MG_1458881516931头图背景1.png","sale_num":1153,"id":36832,"rebet_amount":0},{"agent_price":58.9,"in_customer_shop":0,"name":"显瘦修身铅笔裤/白色九分","std_sale_price":298,"remain_num":180,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"3420人在卖","pic_path":"http://image.xiaolu.so/MG_1458903369832显瘦修身铅笔裤02.png","sale_num":3420,"id":36830,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"时尚拼接假两件套装/图片色","std_sale_price":299,"remain_num":20,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"389人在卖","pic_path":"http://image.xiaolu.so/MG_1458979005396头图背景拷贝副本.png","sale_num":389,"id":36829,"rebet_amount":0},{"agent_price":58.9,"in_customer_shop":0,"name":"复古破洞小脚裤/浅蓝色","std_sale_price":298,"remain_num":180,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"3421人在卖","pic_path":"http://image.xiaolu.so/MG_1458903398055复古破洞小脚裤03.png","sale_num":3421,"id":36825,"rebet_amount":0},{"agent_price":89.9,"in_customer_shop":0,"name":"韩版时尚运动两件套/红色","std_sale_price":399,"remain_num":20,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"397人在卖","pic_path":"http://image.xiaolu.so/MG_1458892931835韩版时尚运动两件套02.png","sale_num":397,"id":36822,"rebet_amount":0},{"agent_price":179.9,"in_customer_shop":0,"name":"印花蕾丝雪纺连衣裙/白色","std_sale_price":297,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"966人在卖","pic_path":"http://image.xiaolu.so/MG_1458813272410头图2.png","sale_num":966,"id":36820,"rebet_amount":0},{"agent_price":199.9,"in_customer_shop":0,"name":"韩版宽松显瘦外套/浅蓝色","std_sale_price":330,"remain_num":40,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"764人在卖","pic_path":"http://image.xiaolu.so/MG_1458805072014头图.png","sale_num":764,"id":36819,"rebet_amount":0},{"agent_price":49.9,"in_customer_shop":0,"name":"美都汇韩版印花T恤/图片色","std_sale_price":245,"remain_num":31,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"601人在卖","pic_path":"http://image.xiaolu.so/MG_1458286828718头图背景1.png","sale_num":601,"id":36521,"rebet_amount":0},{"agent_price":99.9,"in_customer_shop":0,"name":"美都汇简洁经典七分袖上衣/白色","std_sale_price":718,"remain_num":33,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"644人在卖","pic_path":"http://image.xiaolu.so/MG_1458282077057头图背景1.png","sale_num":644,"id":36457,"rebet_amount":0},{"agent_price":99.9,"in_customer_shop":0,"name":"美都汇减龄显瘦小V领蕾丝衫/白色","std_sale_price":738,"remain_num":20,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"394人在卖","pic_path":"http://image.xiaolu.so/MG_1458282034067头图背景1.png","sale_num":394,"id":36456,"rebet_amount":0},{"agent_price":99.9,"in_customer_shop":0,"name":"美都汇黑白条纹针织衫/图片色","std_sale_price":688,"remain_num":15,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"286人在卖","pic_path":"http://image.xiaolu.so/MG_1458290271840头图背景1.png","sale_num":286,"id":36449,"rebet_amount":0},{"agent_price":119.9,"in_customer_shop":0,"name":"美都汇韩版撞色百搭套装/图片色","std_sale_price":668,"remain_num":16,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"310人在卖","pic_path":"http://image.xiaolu.so/MG_1458288661596头图背景1.png","sale_num":310,"id":36440,"rebet_amount":0},{"agent_price":89.9,"in_customer_shop":0,"name":"美都汇时尚条纹拼接裙/黑色","std_sale_price":618,"remain_num":17,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"334人在卖","pic_path":"http://image.xiaolu.so/MG_1458293442649头图背景1.png","sale_num":334,"id":36395,"rebet_amount":0},{"agent_price":89.9,"in_customer_shop":0,"name":"美都汇印花哈伦休闲裤/黑色","std_sale_price":558,"remain_num":26,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"496人在卖","pic_path":"http://image.xiaolu.so/MG_1458350704535头图背景1拷贝.png","sale_num":496,"id":36388,"rebet_amount":0},{"agent_price":99.9,"in_customer_shop":0,"name":"美都汇韩版时尚宽松风衣/粉色","std_sale_price":788,"remain_num":14,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"275人在卖","pic_path":"http://image.xiaolu.so/MG_1458350646528头图背景1拷贝.png","sale_num":275,"id":36386,"rebet_amount":0},{"agent_price":99.9,"in_customer_shop":0,"name":"新款印花棒球服/白花朵","std_sale_price":459,"remain_num":150,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"2858人在卖","pic_path":"http://image.xiaolu.so/MG_14573403937121.png","sale_num":2858,"id":34896,"rebet_amount":0},{"agent_price":49.9,"in_customer_shop":0,"name":"丰满聚拢无钢圈文胸套装/黑+灰","std_sale_price":168,"remain_num":155,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"2949人在卖","pic_path":"http://image.xiaolu.so/MG_14591327479474.png","sale_num":2949,"id":34657,"rebet_amount":0},{"agent_price":39.9,"in_customer_shop":0,"name":"三条装高腰提臀内裤/三条装","std_sale_price":118,"remain_num":233,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"4442人在卖","pic_path":"http://image.xiaolu.so/MG_145725977627011111.png","sale_num":4442,"id":34656,"rebet_amount":0},{"agent_price":39.9,"in_customer_shop":0,"name":"经典百搭破洞牛仔裤/浅蓝色","std_sale_price":168,"remain_num":450,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"8558人在卖","pic_path":"http://image.xiaolu.so/MG_1458997584839主图2.png","sale_num":8558,"id":34499,"rebet_amount":0},{"agent_price":79.9,"in_customer_shop":0,"name":"韩版双龙闪电印花套装/酒红色","std_sale_price":129,"remain_num":50,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"952人在卖","pic_path":"http://image.xiaolu.so/MG_1456910067880韩版双龙闪电印花套装03.png","sale_num":952,"id":34191,"rebet_amount":0},{"agent_price":59.9,"in_customer_shop":0,"name":"青春活泼运动裙/黄色","std_sale_price":299,"remain_num":90,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1715人在卖","pic_path":"http://image.xiaolu.so/MG_14558708680444.png","sale_num":1715,"id":33132,"rebet_amount":0},{"agent_price":59.9,"in_customer_shop":0,"name":"韩版蕾丝连衣裙/黑色","std_sale_price":299,"remain_num":75,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1430人在卖","pic_path":"http://image.xiaolu.so/MG_14558671334943.png","sale_num":1430,"id":33111,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"甜美公主连衣裙/黑色","std_sale_price":349,"remain_num":75,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1429人在卖","pic_path":"http://image.xiaolu.so/MG_14558670739242.png","sale_num":1429,"id":33106,"rebet_amount":0},{"agent_price":89.9,"in_customer_shop":0,"name":"时尚潮品休闲套装/黑色","std_sale_price":449,"remain_num":75,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"1439人在卖","pic_path":"http://image.xiaolu.so/MG_14558670230841.png","sale_num":1439,"id":33097,"rebet_amount":0},{"agent_price":119.9,"in_customer_shop":0,"name":"美都汇韩版印花运动套装/黑色","std_sale_price":828,"remain_num":25,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"479人在卖","pic_path":"http://image.xiaolu.so/MG_1458283582667头图背景1.png","sale_num":479,"id":33047,"rebet_amount":0},{"agent_price":69.9,"in_customer_shop":0,"name":"美都汇名媛修身蕾丝裙/黑色","std_sale_price":528,"remain_num":33,"shop_product_num":4,"rebet_amount_des":"佣 ￥0","sale_num_des":"635人在卖","pic_path":"http://image.xiaolu.so/MG_1455877501737名媛修身蕾丝连衣裙01.png","sale_num":635,"id":32947,"rebet_amount":0}]
   */

  @SerializedName("count")
  private int count;
  @SerializedName("next")
  private String next;
  @SerializedName("previous")
  private Object previous;
  /**
   * agent_price : 45.0
   * in_customer_shop : 0
   * name : 韩版紧身五分袖T恤/酒红色
   * std_sale_price : 198.0
   * remain_num : 200
   * shop_product_num : 4
   * rebet_amount_des : 佣 ￥0
   * sale_num_des : 3804人在卖
   * pic_path : http://image.xiaolu.so/MG_1458971041457头图背景1.png
   * sale_num : 3804
   * id : 37021
   * rebet_amount : 0
   */

  @SerializedName("results")
  private List<ResultsEntity> results;

  public void setCount(int count) {
    this.count = count;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public void setPrevious(Object previous) {
    this.previous = previous;
  }

  public void setResults(List<ResultsEntity> results) {
    this.results = results;
  }

  public int getCount() {
    return count;
  }

  public String getNext() {
    return next;
  }

  public Object getPrevious() {
    return previous;
  }

  public List<ResultsEntity> getResults() {
    return results;
  }

  public static class ResultsEntity {
    @SerializedName("agent_price")
    private double agentPrice;
    @SerializedName("in_customer_shop")
    private int inCustomerShop;
    @SerializedName("name")
    private String name;
    @SerializedName("std_sale_price")
    private double stdSalePrice;
    @SerializedName("remain_num")
    private int remainNum;
    @SerializedName("shop_product_num")
    private int shopProductNum;
    @SerializedName("rebet_amount_des")
    private String rebetAmountDes;
    @SerializedName("sale_num_des")
    private String saleNumDes;
    @SerializedName("pic_path")
    private String picPath;
    @SerializedName("sale_num")
    private int saleNum;
    @SerializedName("id")
    private int id;
    @SerializedName("rebet_amount")
    private int rebetAmount;

    public void setAgentPrice(double agentPrice) {
      this.agentPrice = agentPrice;
    }

    public void setInCustomerShop(int inCustomerShop) {
      this.inCustomerShop = inCustomerShop;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setStdSalePrice(double stdSalePrice) {
      this.stdSalePrice = stdSalePrice;
    }

    public void setRemainNum(int remainNum) {
      this.remainNum = remainNum;
    }

    public void setShopProductNum(int shopProductNum) {
      this.shopProductNum = shopProductNum;
    }

    public void setRebetAmountDes(String rebetAmountDes) {
      this.rebetAmountDes = rebetAmountDes;
    }

    public void setSaleNumDes(String saleNumDes) {
      this.saleNumDes = saleNumDes;
    }

    public void setPicPath(String picPath) {
      this.picPath = picPath;
    }

    public void setSaleNum(int saleNum) {
      this.saleNum = saleNum;
    }

    public void setId(int id) {
      this.id = id;
    }

    public void setRebetAmount(int rebetAmount) {
      this.rebetAmount = rebetAmount;
    }

    public double getAgentPrice() {
      return agentPrice;
    }

    public int getInCustomerShop() {
      return inCustomerShop;
    }

    public String getName() {
      return name;
    }

    public double getStdSalePrice() {
      return stdSalePrice;
    }

    public int getRemainNum() {
      return remainNum;
    }

    public int getShopProductNum() {
      return shopProductNum;
    }

    public String getRebetAmountDes() {
      return rebetAmountDes;
    }

    public String getSaleNumDes() {
      return saleNumDes;
    }

    public String getPicPath() {
      return picPath;
    }

    public int getSaleNum() {
      return saleNum;
    }

    public int getId() {
      return id;
    }

    public int getRebetAmount() {
      return rebetAmount;
    }
  }
}
