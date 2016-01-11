package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductListBean {

  /**
   * count : 46
   * next : null
   * previous : null
   * results : [{"id":12171,"url":"http://m.xiaolu.so/rest/v1/products/12171.json","name":"秒杀 韩版撞色时尚条纹系列套装/系列1","outer_id":"80301521301","category":{"cid":22,"parent_cid":8,"name":"套装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLukME8TkAGP3Z5UNh7ncA5ll5edUFibibiaQhj2aSJEN9KIQO2zqIg8GdJ0fuUA6cj1lLibC6HTfxgByw/0?wx_fmt=png","remain_num":5,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451396496306-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":430,"agent_price":59.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":59.9,"product_lowest_price":59.9,"product_model":{"id":6811,"name":"秒杀 韩版撞色时尚条纹系列套装","head_imgs":["http://image.xiaolu.so/MG-1451396496306-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQib9pascibFYl1iaMlc587ox9fqU2J5Jbq1tMbWAQte8PZ8oCLhRw0pYnQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQUtcla1fCLlg82yUAOnibDRW04Dwag0cWibOlq4ZiaiaTmymhuD3IJibibecw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQWIBdubgwcDXyLhtcSOhQhrz6rjogibZrHztQbClVcXKp9L0yetvianIw/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":true,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6811},{"id":18880,"url":"http://m.xiaolu.so/rest/v1/products/18880.json","name":"秒杀 冰丝针织衫+时尚哈伦裤套装/白色","outer_id":"822237740011","category":{"cid":22,"parent_cid":8,"name":"套装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLticFgKr4f1gC7d8y5CUCPWejKAxMfy51vq1P5Fy7vr5BILTibHZYxo8IfYdQCDo0udpgmlEic3dVAsw/0?wx_fmt=png","remain_num":7,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451397821982-6.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":499,"agent_price":59.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":59.9,"product_lowest_price":59.9,"product_model":{"id":2333,"name":"秒杀 冰丝针织衫+时尚哈伦裤套装","head_imgs":["http://image.xiaolu.so/MG-1451397821982-6.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLticFgKr4f1gC7d8y5CUCPWeqzba6ouBo0h0JYDpD0AhQyUzXaFHDdK73mj1Rz6AJqUFLGQwcMtic5g/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":2333},{"id":22279,"url":"http://m.xiaolu.so/rest/v1/products/22279.json","name":"韩版竖条纹哈伦裤/黑色","outer_id":"821272010011","category":{"cid":21,"parent_cid":8,"name":"下装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsEBYQ5iaAicNmrWVB5dpibIHN7BmXEia3tHrbbCiarBISCsdibVMiaCq8EDoUWaZl3RFLsic044f3wsgFlPw/0?wx_fmt=png","remain_num":4,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451559772854-1.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":299,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":3600,"name":"韩版竖条纹哈伦裤","head_imgs":["http://image.xiaolu.so/MG-1451559772854-1.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsEBYQ5iaAicNmrWVB5dpibIHN8unwiaBKHMmUCIfyOfHtz3fLJjJssKicKhvgvQXdxGjGzNdMvGpllriaQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsEBYQ5iaAicNmrWVB5dpibIHNRvDtib45tjuRRSxtLkxQibJrv1PdD2KcanNWtaIIlWbntfibkyobUV6iaA/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":3600},{"id":9553,"url":"http://m.xiaolu.so/rest/v1/products/9553.json","name":"秒杀 时尚蝴蝶休闲套装/白色","outer_id":"80302500301","category":{"cid":8,"parent_cid":4,"name":"女装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtV6ldibVMO7kYN5f87DstVkibLF5MrtZF2ZWavnrTjSb5lpj8ws9Mm4mwsU9Bpo71pLK3qu71Ks1Fg/0?wx_fmt=png","remain_num":1,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451397613744-5.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":339,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":1244,"name":"秒杀 时尚蝴蝶休闲套装","head_imgs":["http://image.xiaolu.so/MG-1451397613744-5.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtV6ldibVMO7kYN5f87DstVkYa3SicQ5QiaqiaJIkgHo7p3EEcpwibwiaF1ick94rWZyfNOho9sg3XqfBBWQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtV6ldibVMO7kYN5f87DstVkf9QXeO2ZkcD1062xnL9hBZBkQO1EvwZndwjycdp6VFkaFOYuicwtYqw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtV6ldibVMO7kYN5f87DstVkFcWRCVmMkvl0DkSwUpwG2OucaaUic6xfibutP5FMlJBqWKY0pkQWGH0w/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":1244},{"id":12009,"url":"http://m.xiaolu.so/rest/v1/products/12009.json","name":"秒杀 时尚条纹运动套装/黑色","outer_id":"80302705701","category":{"cid":22,"parent_cid":8,"name":"套装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYPnNWeNOY1JvRz7JYV3Fexxsd5UrpAcOjVjQSRGVcjIx722q6ONO1Lhw5NN5ictza4rP1amTjGnQ/0?wx_fmt=png","remain_num":6,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396478240-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":349,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":1058,"name":"秒杀 时尚条纹运动套装","head_imgs":["http://image.xiaolu.so/MG-1451396478240-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvay5qmQhtyGjXQugJGuTOdwT80Y3VFp0CSNcicCyIYVqD4ibgrtXESgqdNXLCs2sF2sS6D1syvWicPg/0?wx_fmt=jpeg"],"is_single_spec":true,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":1058},{"id":13108,"url":"http://m.xiaolu.so/rest/v1/products/13108.json","name":"威摩士时尚针织衫-米色","outer_id":"80202486201","category":{"cid":20,"parent_cid":8,"name":"上装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLthLIqUKPl1uk97zHZvWBOqVeMxKNQP8FO1j7uaicUR1L75iaSC2JbRusOpUOOKt9SBWCsK33uJIIuA/0?wx_fmt=png","remain_num":5,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451563781947-12.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":299,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":6915,"name":"威摩士时尚针织衫","head_imgs":["http://image.xiaolu.so/MG-1451563781947-12.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvfVbcriceDwraqo1wVTvY7fsY4L1xyAZsz8hQuOjic2HdacY5GU1sVheXX5kLd6kwz5aicFV652Smwg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvWkGj5Jjnuc9KwbWFTR6QOobIs34MxCvefoZRY7qhhlwdtludicrMPzo2qYoMuSpPqGtJ9KJPxWEQ/0?wx_fmt=jpeg"],"is_single_spec":true,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6915},{"id":15707,"url":"http://m.xiaolu.so/rest/v1/products/15707.json","name":"秒杀 波普图案连衣裙/白色","outer_id":"814012016001","category":{"cid":19,"parent_cid":8,"name":"连衣裙","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsyAicOriaIXNVf9EOd1QDQQVdgYG7Bk7ria184af4b0NBPX3mdXxWoBg1uJ4acRBx7QeMVBciaNzNlYQ/0?wx_fmt=png","remain_num":12,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396640198-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":299,"agent_price":29.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":29.9,"product_lowest_price":29.9,"product_model":{"id":1240,"name":"秒杀 波普图案连衣裙","head_imgs":["http://image.xiaolu.so/MG-1451396640198-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuaf1JCLQPgPC324rXOIg8FpG0EB7tY4a6hRiadAibIMEIbtnmj1cfbnhGJZsBdMydozcPvba9fSgOQ/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":1240},{"id":15882,"url":"http://m.xiaolu.so/rest/v1/products/15882.json","name":"秒杀 修身款西服外套/宝蓝色","outer_id":"815012033801","category":{"cid":18,"parent_cid":8,"name":"外套","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuaf1JCLQPgPC324rXOIg8FSv3CqOJicKibOjuUQgibnsqva4puDfCFZ8uLsARpqHBQkPKznKMfefHDw/0?wx_fmt=png","remain_num":0,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451397799637-23.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":389,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":1321,"name":"秒杀 修身款西服外套","head_imgs":["http://image.xiaolu.so/MG-1451397799637-23.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuaf1JCLQPgPC324rXOIg8Fe77OK2UPMlibul3LzPpEwQGjZJBgsqdLaVEFZiaRVY0ZEY7JVOlEtzmA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuaf1JCLQPgPC324rXOIg8FuQJxcRlA9ldwgfz9vialTd7LEVVGPSvXFKqeYiaL5lAUSgiblutxnvOFg/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":true,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":1321},{"id":17606,"url":"http://m.xiaolu.so/rest/v1/products/17606.json","name":"秒杀 韩版撞色针织棒球服/卡其色","outer_id":"81501733901","category":{"cid":18,"parent_cid":8,"name":"外套","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvlAphyfwwyVle7KJbyNjk95Drg3qkGhwshJQaibTtjjbdkXQ28t1FM2icQLVMK3iaOZEmuGPaOvh5XA/0?wx_fmt=png","remain_num":1,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451397858182-15.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":299,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":1855,"name":"秒杀 韩版撞色针织棒球服","head_imgs":["http://image.xiaolu.so/MG-1451397858182-15.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvlAphyfwwyVle7KJbyNjk9kLegomWOXfejc4pu0XPZlVPEUjaV4KPGh8V54b2ticXLYBjFlnUsR8g/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":1855},{"id":18287,"url":"http://m.xiaolu.so/rest/v1/products/18287.json","name":"秒杀 欧美大牌显瘦连衣裙/黑色","outer_id":"819250210011","category":{"cid":19,"parent_cid":8,"name":"连衣裙","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLu4vYWmnHGZ5F16AgJhlwr1DPXOAPjAFToCcoIb1Mnc0UoCw10GGNvM90ok0rfwwbOpNV17HriaYbg/0?wx_fmt=png","remain_num":5,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451397757058-18.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":399,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":2100,"name":"秒杀 欧美大牌显瘦连衣裙","head_imgs":["http://image.xiaolu.so/MG-1451397757058-18.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLu4vYWmnHGZ5F16AgJhlwr1R8R5ap7NibETQfI5Bgr7znsbOuJH3iaU2Hq7JDDibuFWd00ZicTua9rkpA/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":2100},{"id":20547,"url":"http://m.xiaolu.so/rest/v1/products/20547.json","name":"秋季时尚休闲卫衣套装/白色","outer_id":"822262250021","category":{"cid":22,"parent_cid":8,"name":"套装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvfVbcriceDwraqo1wVTvY7f7EcboBIzkyGNgB6l221NTxnmc5ZfVkmXPQmyjav1cToyV0icKiaR8vcg/0?wx_fmt=png","remain_num":3,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451564463899-7.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":549,"agent_price":79.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":79.9,"product_lowest_price":79.9,"product_model":{"id":2931,"name":"秋季时尚休闲卫衣套装","head_imgs":["http://image.xiaolu.so/MG-1451564463899-7.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvfVbcriceDwraqo1wVTvY7faf1Yctpwr6V0t6IWAtJVMwmicscOe2kjOaYEnjojhyPaOSsxEH7icPfQ/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":2931},{"id":20713,"url":"http://m.xiaolu.so/rest/v1/products/20713.json","name":"针织棒球服开叉裙套装/白色","outer_id":"822261360011","category":{"cid":22,"parent_cid":8,"name":"套装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLugjW65rmEbTb2KGMPA2R8hvDlDaCD9U0pgIIVsta0CIjwGD53sIYx8ekgRBmhXIeiabdg6EzEfXpA/0?wx_fmt=png","remain_num":2,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451560329104-13.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":549,"agent_price":79.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":79.9,"product_lowest_price":79.9,"product_model":{"id":2992,"name":"针织棒球服开叉裙套装","head_imgs":["http://image.xiaolu.so/MG-1451560329104-13.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLugjW65rmEbTb2KGMPA2R8h6vOh7SDTwPmXdEs2ZAzQhAL7nx7pFtN0zWE1TVicz0gTf7NM79Ra0tg/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":2992},{"id":21037,"url":"http://m.xiaolu.so/rest/v1/products/21037.json","name":"韩版衬衫+马甲两件套/黑色","outer_id":"820263330011","category":{"cid":20,"parent_cid":8,"name":"上装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt7YMYhjrHxcMePelabrGhKg5IfRq2oR1OJ2ic3QB3x1vDvlv42ylDia5z1XVJletVHC0zH7mlDiauUw/0?wx_fmt=png","remain_num":0,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451564156869-2.png","is_saleopen":true,"is_newgood":true,"std_sale_price":499,"agent_price":69.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":69.9,"product_lowest_price":69.9,"product_model":{"id":3115,"name":"韩版衬衫+马甲两件套","head_imgs":["http://image.xiaolu.so/MG-1451564156869-2.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt7YMYhjrHxcMePelabrGhKSlVEHoG6mib2JQuhK05s0ncaWb4Mib87Td8oHQs6NWZwRPHicTOjFbiaxw/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":3115},{"id":21228,"url":"http://m.xiaolu.so/rest/v1/products/21228.json","name":"时尚连帽加厚长款拉链卫衣/黑色","outer_id":"818266520011","category":{"cid":18,"parent_cid":8,"name":"外套","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvV2TdjMkomcuDOY82koJOrmhngA4mV0QHUoCZjiaFdzblbLicQ8H0aUEUzicjSicInkbaicmhSicNUBnCg/0?wx_fmt=png","remain_num":0,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451564714249-9.jpg","is_saleopen":true,"is_newgood":true,"std_sale_price":549,"agent_price":79.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":79.9,"product_lowest_price":79.9,"product_model":{"id":3183,"name":"时尚连帽加厚长款拉链卫衣","head_imgs":["http://image.xiaolu.so/MG-1451564714249-9.jpg"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvV2TdjMkomcuDOY82koJOrj6x260Wib616CNZymNZdviaVy1zXRzv6FibicnEggictiad5CsPvo6vicPaGA/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvV2TdjMkomcuDOY82koJOrLBSjSn17lpSZrPm4ibR3EeZRQe9tI4BAuUj2h8KIy8rM4KVQ9EZ3FIQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvV2TdjMkomcuDOY82koJOrlwjXeUw8uCoZyT3LBQMraK4icrLWHbmBIBUH309zdB1wnUVcsl9EAeA/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":3183},{"id":22082,"url":"http://m.xiaolu.so/rest/v1/products/22082.json","name":"韩版流苏披肩式针织衣/墨绿色","outer_id":"820263400011","category":{"cid":20,"parent_cid":8,"name":"上装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuEZRxcZp90Kxq7ycrNc81wKpILLicL1VicNf6nXBWEBUXSLpiaAn6icuzULk9ibetuH7EC6sBiaYHCZYhA/0?wx_fmt=png","remain_num":3,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451564629992-头图.png","is_saleopen":true,"is_newgood":true,"std_sale_price":349,"agent_price":59.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":59.9,"product_lowest_price":59.9,"product_model":{"id":3521,"name":"韩版流苏披肩式针织衣","head_imgs":["http://image.xiaolu.so/MG-1451564629992-头图.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuEZRxcZp90Kxq7ycrNc81w4pLgM5zFDbrTNOsejM5s3cU9icZ5t3ibjiaHDqicetpeotAXAC28qCkgGg/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":true,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":3521},{"id":22835,"url":"http://m.xiaolu.so/rest/v1/products/22835.json","name":"韩版抽象印花毛衣/白色","outer_id":"820245790011","category":{"cid":20,"parent_cid":8,"name":"上装","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmbxlWdhzxaRo1ibd2yd64duGltWJETGZ5ODmz6WJ7eOFguNvIabU9Stw/0?wx_fmt=png","remain_num":3,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451560221522-1.png","is_saleopen":true,"is_newgood":true,"std_sale_price":349,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":3815,"name":"韩版抽象印花毛衣","head_imgs":["http://image.xiaolu.so/MG-1451560221522-1.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuRiakAVib8CCvEPdYiazodMFmrG1xvgq7CWuPhqJCyRhaXJdCrYcxJZgm6DsPEDib7WZEQ02c6kFuIRQ/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":true,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":3815},{"id":23134,"url":"http://m.xiaolu.so/rest/v1/products/23134.json","name":"气质娃娃领连衣裙/黑色","outer_id":"819275730011","category":{"cid":19,"parent_cid":8,"name":"连衣裙","status":"normal","sort_order":100},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/ZYmW1WlFwHyuW0dOqSjEticvSYHSE2Sm4Z86HTo2kn6ePFe8KNp3wkkxFiaZV7GkdKTr3gmnm7HNg41LcGjaYm2g/0?wx_fmt=png","remain_num":1,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451563915868-1.png","is_saleopen":true,"is_newgood":true,"std_sale_price":349,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":3913,"name":"气质娃娃领连衣裙","head_imgs":["http://image.xiaolu.so/MG-1451563915868-1.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/ZYmW1WlFwHyuW0dOqSjEticvSYHSE2Sm4FtUwEmGxOFYS3PiaYC3D2cJRiaUurqYMWevjMS3EUVf5P91RJIkhIaqw/0?wx_fmt=jpeg","http://mmsns.qpic.cn/mmsns/ZYmW1WlFwHyuW0dOqSjEticvSYHSE2Sm4ibhYicYugIiaQiamcjxM9LSOJw/0","https://mmbiz.qlogo.cn/mmbiz/ZYmW1WlFwHyuW0dOqSjEticvSYHSE2Sm418MPzVfthtkPxZnfog1icEuYejauSUlPXyszU1DvM6V6taECLC23skQ/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":3913},{"id":2467,"url":"http://m.xiaolu.so/rest/v1/products/2467.json","name":"秒杀 纯棉迷彩哈伦童裤/红色","outer_id":"902032301","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLv8iadYrZfuxhWWK8wW9pgBa1ENfrich4JgViayMahqpZIzmWRQ6yP4rR76emRCvqVZWUKBia9lsFGqlg/0?wx_fmt=jpeg","remain_num":11,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451460180598-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":199,"agent_price":29.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":29.9,"product_lowest_price":29.9,"product_model":{"id":6807,"name":"秒杀 纯棉迷彩哈伦童裤","head_imgs":["http://image.xiaolu.so/MG-1451460180598-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLv8iadYrZfuxhWWK8wW9pgBaZt9CkvcQ2F6AmrdhyDQeDUv0jaF2PaayK91iaAIYWx37ypDEdR9tQicQ/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLv8iadYrZfuxhWWK8wW9pgBaD8Pmsib8QKgXXRiaO7SfFHz0t0uggBEibFErvXyhsx3iboll74RibX0AU5A/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLv8iadYrZfuxhWWK8wW9pgBaoTARN9PSus9h4ghID8ZiaPw32lyIQEvPZASQAdiawuVTp5JicG9eGVpwg/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLv8iadYrZfuxhWWK8wW9pgBa3zbm7J0K0UFgK6T4jQB94pYwnY2Yt1KfRFRfic9HFPYfZUchodpvDiaQ/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6807},{"id":8894,"url":"http://m.xiaolu.so/rest/v1/products/8894.json","name":"秒杀 外贸TOMM*针织牛仔裤/蓝色","outer_id":"90201400601","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvo6MXB6Ftib8g1lx0XvHyBtIOO0ulsfJqfF5FuEmI4Lk7KGLC6X0RTbhRVm4MFlLyzQM3DokZFukQ/0?wx_fmt=png","remain_num":10,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396243611-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":299,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":6808,"name":"秒杀 外贸TOMM*针织牛仔裤","head_imgs":["http://image.xiaolu.so/MG-1451396243611-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvo6MXB6Ftib8g1lx0XvHyBtNNUpEianUMBNnyBupt2Zunicx8950MydCRaIdCfW5VTXkiaptMM7yuatg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvo6MXB6Ftib8g1lx0XvHyBtkxadULYRtjH5Jjo615nN2TW0vBPG2Vw67NVibr9xiaZS4umObDb9B9RQ/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvo6MXB6Ftib8g1lx0XvHyBtkBEdB2PLBBGgj509JicRyny4lujAT4fyoZIvyOq4ia2Q4aKf4FsYjdDA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvo6MXB6Ftib8g1lx0XvHyBtXHAhJfdCEia1KMXicRnKmpaGrRiaUeibZFG77EPVx4y4RL5UcicK7icLrgibQ/0?wx_fmt=jpeg"],"is_single_spec":true,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6808},{"id":9047,"url":"http://m.xiaolu.so/rest/v1/products/9047.json","name":"秒杀 甜美纱纱公主半裙/白色","outer_id":"9020682301","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB30WiaP9vPDpgX1TjCR29ibPpngm3DhwalhCneRze5icjpNibMSy9LMEHHwRA/0?wx_fmt=png","remain_num":5,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396263837-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":239,"agent_price":29.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":29.9,"product_lowest_price":29.9,"product_model":{"id":6810,"name":"秒杀 甜美纱纱公主半裙","head_imgs":["http://image.xiaolu.so/MG-1451396263837-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB305FicjH4jPRQpCNxPGvrVcnWnNthiaYO13MtvE4fGPvyrWMfibHP0Xccrg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB30aExfQnTTfY2jMj1Pt2ThbMQSmL2xakD0OTQ8ykDdvzS1lyIgRCoVicQ/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB302jpRibxkmZ6wtpIDv7HX5ap4gFpc5uNu3wEp3rOVricse6khVJqGibWWw/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB30J3Omr9YgGaqnkxzXibMAdazr9iblvCvYzQlFVnbpCPjSbia6KyJFvHU1g/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6810},{"id":9083,"url":"http://m.xiaolu.so/rest/v1/products/9083.json","name":"秒杀 潮流字母卷边套装/白色","outer_id":"9030483101","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB30hvHLibs2b4iccOF8ibCPIiaG77CV2nDegAfnqjOrG2APsyIaHVhh0TgYeg/0?wx_fmt=png","remain_num":3,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396101068-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":199,"agent_price":29.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":29.9,"product_lowest_price":29.9,"product_model":{"id":6799,"name":"秒杀 潮流字母卷边套装","head_imgs":["http://image.xiaolu.so/MG-1451396101068-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB30O5HthfbCYDiclSAnC2Uy8caAabgibhbcnodiab6UQTMNT6F0zKmT3knFQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB30RoXfV3zaPuBtsWwL2xteQbPkkxjrMYnH4P3LY7S9qnzkdxHow9TsMA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB30swbmR0bRyv0ydXXMZ3uRTSNPpDwYhhic0BXxyZwmQE34eB2dfYTZ1dg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtYrtia0wncyR48IbnmlhB30I3IWV6AHp0tMzDzxVZIubtPgf2tC6uUnPgQPhKpg2bick50vlNXHicOw/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6799},{"id":9505,"url":"http://m.xiaolu.so/rest/v1/products/9505.json","name":"秒杀 萌宝条纹网纱连衣裙/黑白条纹","outer_id":"90202654301","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsMrwK9zUojrCPz7vawNeh9xrf23kCU9OGLxEpyfLvF17dKfcdRR2Ne39My4Sdy0HuH1ROFkrfRPg/0?wx_fmt=png","remain_num":8,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396202377-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":239,"agent_price":29.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":29.9,"product_lowest_price":29.9,"product_model":{"id":6803,"name":"秒杀 萌宝条纹网纱连衣裙","head_imgs":["http://image.xiaolu.so/MG-1451396202377-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsMrwK9zUojrCPz7vawNeh91v6BvQ8JPpH5giaOvv4Fnz3bws71ttdQ6M4Xat4o5Mou2xFS7ceC2zg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsMrwK9zUojrCPz7vawNeh92BiaKgiaZpfcUMwiaAdDQBeMV2OJsrmh5B4NERA2sn0GUVicJGbiaXfNicicA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsMrwK9zUojrCPz7vawNeh9QGR0rTrVO5ghKicgzwXWDkc7c83sPaTIYXuzMZAia5xtlSL5PpYcFtnA/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6803},{"id":9755,"url":"http://m.xiaolu.so/rest/v1/products/9755.json","name":"秒杀  韩版条纹休闲哈伦裤/花灰色","outer_id":"90201661401","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt25WkorVsNXGSicT1NEBPDLGX1M6rYaIxBus3dpYnaVAiaKQGCSt4SORdpQ9U58S64ovjTnkchfZDg/0?wx_fmt=png","remain_num":4,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451395960346-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":199,"agent_price":29.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":29.9,"product_lowest_price":29.9,"product_model":{"id":6797,"name":"秒杀  韩版条纹休闲哈伦裤","head_imgs":["http://image.xiaolu.so/MG-1451395960346-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt25WkorVsNXGSicT1NEBPDLHMDpnicBWtlWM87ia0JdG5RyO3Vl1OUx3bDSKbCNEvypvMqstblVH13g/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt25WkorVsNXGSicT1NEBPDL9qnQ7YO0NhONX69KrStKiaYQ9XuxsPrXq1Y2dntCiaeib48vJ5EZ4OIVw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt25WkorVsNXGSicT1NEBPDLJJFwvRf13XyYw1oo9cdjSuCoTuCwYuEXwibJzTAtVJHjF6mU0lyoTsw/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt25WkorVsNXGSicT1NEBPDLzCGNzQxB8bM6p9kS0w8aEGP6uibVFkIHVnFmjKAWRsb9lSnibcbyh9lg/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6797},{"id":9765,"url":"http://m.xiaolu.so/rest/v1/products/9765.json","name":"秒杀 韩版印花背心七分裤套装/黑色","outer_id":"903041458001","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvDfnp7J0j3O8ibmttq6cMKzmcuodkYTMILhd8lJLqiaodrHomO2ZmuTTKQ5r7g6EKy5qDhYQ9UEedQ/0?wx_fmt=png","remain_num":4,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396219828-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":249,"agent_price":29.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":29.9,"product_lowest_price":29.9,"product_model":{"id":2,"name":"秒杀 韩版印花背心七分裤套装","head_imgs":["http://image.xiaolu.so/MG-1451396219828-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvDfnp7J0j3O8ibmttq6cMKzBb2iaa8kh0oyz03sKB5v3Yeic9jyEoKiapDP7ogGvk24EIHPkqbMrKDhQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvDfnp7J0j3O8ibmttq6cMKzbmtmF4J57S3Lx2d7HLqKiafrHVicWWybrNBk5YykagZkrXdpqJ7wKQYw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvDfnp7J0j3O8ibmttq6cMKzia4fSTOYcvAuJ52Eibv2kOeB8ypAzI5BQkgDRSBkh5u9zsJhCsSccauA/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":2},{"id":9976,"url":"http://m.xiaolu.so/rest/v1/products/9976.json","name":"秒杀 韩版精品帅气T恤/白色","outer_id":"9040822301","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXHyZUuDGchSXKCnRUVCIgibe4XkzC2PuKsmFBfIEHbfJnIEtTH9cyEuQ/0?wx_fmt=png","remain_num":5,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396308970-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":145,"agent_price":19.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":19.9,"product_lowest_price":19.9,"product_model":{"id":6809,"name":"秒杀 韩版精品帅气T恤","head_imgs":["http://image.xiaolu.so/MG-1451396308970-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXyDhDAxeicgFFVkxibTYdrHC7oOhj2wmlM86GPrfGNHbLn2oAuZYiblVIQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXg3ibHG8IicMJRNVZnLhafG1QqefwOzullthaVAfuVQUy8yodsibmjjo5w/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXDzbkwEoq0xs4EQMqa0jOVCNM6icibH6T7TptYriaIIM2ib0U5JHjebCseg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLspPdDiclj3a8C415fxxLDaXsrwYFnYF4PD1okOxvqAL2G0rba5lM8C3iamUQc8LPpY9fD62yNBdHHA/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6809},{"id":10416,"url":"http://m.xiaolu.so/rest/v1/products/10416.json","name":"秒杀 英伦徽章休闲西服/灰色","outer_id":"90402659801","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsoZuW6yClnccEKbMTgW3JwVfibKlFMkDUOxEPw0BAI1J4NAncDDA0GKDZB5Wnk7OZR2aQEJzOR1eg/0?wx_fmt=png","remain_num":4,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396352874-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":299,"agent_price":59.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":59.9,"product_lowest_price":59.9,"product_model":{"id":6804,"name":"秒杀 英伦徽章休闲西服","head_imgs":["http://image.xiaolu.so/MG-1451396352874-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsoZuW6yClnccEKbMTgW3JwDZbf3XIlUmQ8ZF3ibKPAzQibMy7lmmEYT0YYC4G0kibZE21gV32kCSyHQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsoZuW6yClnccEKbMTgW3JwNadfmGiacOXThQibkhByJL8SHp8CZCkf5LN6fIB0QGvGjCIFicR0gibyyA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsoZuW6yClnccEKbMTgW3JwIl0T2RvPPFUgrPn57pzaZDro9gfIHqKXTEh1lOpwTHWWzYookSX3JQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsoZuW6yClnccEKbMTgW3JwX5Al7gyIiaibRw2z6UWDDSdwXVyPb6kdEFuXAUXH3aZ7F1wCPibZC3bLA/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6804},{"id":15375,"url":"http://m.xiaolu.so/rest/v1/products/15375.json","name":"韩版潮流牛仔风衣外套/深蓝色","outer_id":"9150196201","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs5dk1nGDdeaUKQFGTtRsPHJialMvs5JMsmrbuM2TPflNDDIwu28KZw3r5oNGxIF5feh0rFlIAGiaHg/0?wx_fmt=png","remain_num":9,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451556031428-0.png","is_saleopen":true,"is_newgood":true,"std_sale_price":349,"agent_price":59.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":59.9,"product_lowest_price":59.9,"product_model":{"id":6895,"name":"韩版潮流牛仔风衣外套","head_imgs":["http://image.xiaolu.so/MG-1451556031428-0.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsUOicv2nCge2MsJB11IOLRbzOASugtShFAqe3qNGWj3u0TCKnsVTQkOKibdqOxwuibFEVr5BibQh8HGQ/0?wx_fmt=jpeg"],"is_single_spec":true,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6895},{"id":10193,"url":"http://m.xiaolu.so/rest/v1/products/10193.json","name":"秒杀 卡通猫咪吊带套装/黄色","outer_id":"9030487601","category":{"cid":5,"parent_cid":4,"name":"童装","status":"normal","sort_order":50},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtmyDv6VBabjGQ4nFfsBT96tfR1qvIA0OITNwVEr1r9LicmcRDHn0QVHkBG5RvA1Uz9uRIdo2PRJ2w/0?wx_fmt=png","remain_num":2,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451396336591-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":149,"agent_price":19.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":19.9,"product_lowest_price":19.9,"product_model":{"id":6801,"name":"秒杀 卡通猫咪吊带套装","head_imgs":["http://image.xiaolu.so/MG-1451396336591-0秒杀.png"],"content_imgs":["http://image.xiaolu.so/MG-1451456546411-卡通猫咪吊带套装07.png","http://image.xiaolu.so/MG-1451456550284-卡通猫咪吊带套装-07.jpg","http://image.xiaolu.so/MG-1451456553429-卡通猫咪吊带套装08.png","http://image.xiaolu.so/MG-1451456557441-卡通猫咪吊带套装09.png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6801},{"id":10552,"url":"http://m.xiaolu.so/rest/v1/products/10552.json","name":"秒杀 荷叶边T恤纱裙套装/玫红色","outer_id":"90304672201","category":{"cid":15,"parent_cid":5,"name":"套装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuUEpsvL3icWJ1UfRSPY8mIDuNbaMhCpNW8D2mC8yc9icplUTru8B3FxsgTd8EoTKESTU1AcuPjZChg/0?wx_fmt=png","remain_num":2,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396376396-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":189,"agent_price":19.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":19.9,"product_lowest_price":19.9,"product_model":{"id":6805,"name":"秒杀 荷叶边T恤纱裙套装","head_imgs":["http://image.xiaolu.so/MG-1451396376396-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuUEpsvL3icWJ1UfRSPY8mIDcpSroKE0MTgZZibMrCamN9Q68DBXplfD01iciaoibdsZnpgafK0ZKoawlg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuUEpsvL3icWJ1UfRSPY8mID2dtYmAtRGAM6plYmItCHQaSibvWJqmQ5hhozuDbey8B2d1pJkgvAQag/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuUEpsvL3icWJ1UfRSPY8mIDPEdc9Tgj5Jic4km5qLgtWexhwkarTQbwqk00FkDwYlliaeSOkMrV0dSA/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuUEpsvL3icWJ1UfRSPY8mIDIhadOFtNCjdb7rticUcibLE1ZU6KsIcuicibauhCXmcUVWib5ERHfytXw7Q/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6805},{"id":11113,"url":"http://m.xiaolu.so/rest/v1/products/11113.json","name":"秒杀 DKNY原单宫廷风钉珠连衣裙-花色","outer_id":"9020236401","category":{"cid":14,"parent_cid":5,"name":"连衣裙","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtDbpJJqj74wkScTwUFBuaG12ByQicka21kibpUzDV2QbDZl8PMjNZsh4ZsHPTAZ0SA48852ePS6cRA/0?wx_fmt=png","remain_num":10,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451397083924-秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":499,"agent_price":59.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":59.9,"product_lowest_price":59.9,"product_model":{"id":6839,"name":"秒杀 DKNY原单宫廷风钉珠连衣裙","head_imgs":["http://image.xiaolu.so/MG-1451397083924-秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtDbpJJqj74wkScTwUFBuaGgj6AYUp4yMdFurrYia04LDaudmXQ9nbQuqib1w4lLq2zA1enAIbdib4Wg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtDbpJJqj74wkScTwUFBuaGJiaZty3KbBWQjBxicIC9Ysd7nArXC5qV89mcsHbXKqFohLbBQ4gvf8vg/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtDbpJJqj74wkScTwUFBuaGWa2icVnxMofC7icCwrpuSVLtO25044fRkUKicevKonQqF9E2ruf9L1nzg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtDbpJJqj74wkScTwUFBuaGa9icPmYibbTK8uBFgrl8kh14pYCyTm9KpHOqFdYCWw7Jcr0V3ATWsxXA/0?wx_fmt=png"],"is_single_spec":true,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6839},{"id":11270,"url":"http://m.xiaolu.so/rest/v1/products/11270.json","name":"秒杀 外贸卡通猴印花裤套装/灰色","outer_id":"9030472301","category":{"cid":15,"parent_cid":5,"name":"套装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs9ekrR4Znb5tlQVqbI2viaEadQRBZ3zcbIxHhRia4ibr6dTJ9icCYiaTTG0EWxI47U9JTWrdWRwRr1VMQ/0?wx_fmt=png","remain_num":2,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396421621-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":249,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":6806,"name":"秒杀 外贸卡通猴印花裤套装","head_imgs":["http://image.xiaolu.so/MG-1451396421621-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs9ekrR4Znb5tlQVqbI2viaEg4SIWH3MRgCdabGw1dBZkLFPCKlBc2kEsqrw54LSRtFJct9cbwIibcA/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs9ekrR4Znb5tlQVqbI2viaEHkQesza92HGicbBVLL3j0llzxxiaDBJVNckaxialXSy2cWkS2Mz7n573w/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs9ekrR4Znb5tlQVqbI2viaE8gXgOKWyzFDChrmsuA7BZwp8XvRTvoh0kcB4SwlC1pqlAib5WYgNg3g/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLs9ekrR4Znb5tlQVqbI2viaEzwOaPR2xsWVhL6DGaVPk50bFnSPsZTfIhFrQ4hGBFHyr0jLA6SVm7g/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6806},{"id":11445,"url":"http://m.xiaolu.so/rest/v1/products/11445.json","name":"秒杀 格子绅士衬衫/浅蓝格","outer_id":"9040473901","category":{"cid":13,"parent_cid":5,"name":"上装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvIfA4ZWbhILXc7rp9B9Jt3hHV9e4gub8nLZRSYBSZ2Lr616JPtiavM47SxhXKarUjd6tbEQf3Q0TQ/0?wx_fmt=png","remain_num":10,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451396449159-0秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":239,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":6802,"name":"秒杀 格子绅士衬衫","head_imgs":["http://image.xiaolu.so/MG-1451396449159-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvIfA4ZWbhILXc7rp9B9Jt3Al3N2cZqNZXCcy6ia7UZTicCPNyupWAuqG5ytjXRLYNOwpc69PiaKmVZw/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvIfA4ZWbhILXc7rp9B9Jt3e8t2TQYeIicOoF2ddRLArQbyDnznfEGUYAakffPvny09WRib12gfeyqA/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvIfA4ZWbhILXc7rp9B9Jt3Wc1rzFr5YGqmFby6fB05oHeMWNEQPyAIx56veRNSlyCaHXWtmMLfew/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvIfA4ZWbhILXc7rp9B9Jt3X5sLKWpMCtwSXZWibAmHQQZbWRFGbHoJAF0Hr0P4Qy89tkHQr7d1ibUg/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6802},{"id":13587,"url":"http://m.xiaolu.so/rest/v1/products/13587.json","name":"经典时尚牛仔风衣/牛仔蓝","outer_id":"9040277601","category":{"cid":12,"parent_cid":5,"name":"外套","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt7YMYhjrHxcMePelabrGhKicogP1Qgb8fD39444wVUIXzY7E2DNiccXgrP6PkQlGB7oAJXLKhvBrVQ/0?wx_fmt=png","remain_num":8,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451558588941-头图.png","is_saleopen":true,"is_newgood":true,"std_sale_price":349,"agent_price":69.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":69.9,"product_lowest_price":69.9,"product_model":{"id":6914,"name":"经典时尚牛仔风衣","head_imgs":["http://image.xiaolu.so/MG-1451558588941-头图.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt7YMYhjrHxcMePelabrGhKACB5Ggfo7br9ORNRpfX3qAiaD24Aic9CRBjLJ6tsxqd9WptaiaEk8HaWg/0?wx_fmt=jpeg"],"is_single_spec":true,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6914},{"id":16095,"url":"http://m.xiaolu.so/rest/v1/products/16095.json","name":"欧美风精品外套+背心裙2件套/红色大花","outer_id":"9130134001","category":{"cid":15,"parent_cid":5,"name":"套装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLueDQyVtYWufsAecH9nBZLficgnFiaTCuWWoCsu8wo1fKCXW54IpkPCYQQroWib15icFWqeM3XAhGicang/0?wx_fmt=png","remain_num":4,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451557288182-头图.png","is_saleopen":true,"is_newgood":true,"std_sale_price":499,"agent_price":69.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":69.9,"product_lowest_price":69.9,"product_model":{"id":1355,"name":"欧美风精品外套+背心裙2件套","head_imgs":["http://image.xiaolu.so/MG-1451557288182-头图.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvWkGj5Jjnuc9KwbWFTR6QOZ7RFh5Lebg38Zllt5Yjsice0oV7KgOVbSCVSSvDDjrOvWbo72DWkWPQ/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":1355},{"id":16427,"url":"http://m.xiaolu.so/rest/v1/products/16427.json","name":"纯棉条纹V领开衫/黄色","outer_id":"915011801","category":{"cid":12,"parent_cid":5,"name":"外套","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvt4h9EbG1yDs4ueZDgNbwYbOCNCLQycic14j98zvBzf3GiaTYFI47CWiaaJYzgnXH8WBhDv8rBXNz7Q/0?wx_fmt=png","remain_num":3,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451555542872-0.png","is_saleopen":true,"is_newgood":true,"std_sale_price":249,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":1432,"name":"纯棉条纹V领开衫","head_imgs":["http://image.xiaolu.so/MG-1451555542872-0.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvt4h9EbG1yDs4ueZDgNbwYmhaHbEjjHqXEcAWClGDiaK203dMRzsVa3GIMb4OQVEKHPYdbskzhgibg/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":1432},{"id":16495,"url":"http://m.xiaolu.so/rest/v1/products/16495.json","name":"可爱卡通汽车套装/白色","outer_id":"9130138601","category":{"cid":15,"parent_cid":5,"name":"套装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvrj0Ety3QvHmHret6ev9HiciaXbAkOhHkXmnTL2oGn7c7dBsZbPwkhuDC2ck8zgYKpfNyWplezBP6A/0?wx_fmt=png","remain_num":5,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451556225801-头图.png","is_saleopen":true,"is_newgood":true,"std_sale_price":299,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":1481,"name":"可爱卡通汽车套装","head_imgs":["http://image.xiaolu.so/MG-1451556225801-头图.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLurRxXamaBaUsAaIFsCRhQZsxCp9FIP12Sc0uL30N1p73X36HM233686sRVZhcq9nTBAxDNR9uDMA/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":1481},{"id":16844,"url":"http://m.xiaolu.so/rest/v1/products/16844.json","name":"韩版百搭船锚休闲裤/灰色","outer_id":"9120111701","category":{"cid":17,"parent_cid":5,"name":"下装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsrcwibRQ2VK2G0Egb0aeaReqvs5LALvV70ia7ojRm3J7gcibibgu2kshGic51XGicFic7GjJNNM3Pib2NYUg/0?wx_fmt=png","remain_num":2,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451556504804-0.png","is_saleopen":true,"is_newgood":true,"std_sale_price":199,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":1580,"name":"韩版百搭船锚休闲裤","head_imgs":["http://image.xiaolu.so/MG-1451556504804-0.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsrcwibRQ2VK2G0Egb0aeaReQChaKLroMvq4ibjBkpW1vYrooWictaFybNC6SK4vEhcpicEq3T2ndAZsA/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":1580},{"id":18045,"url":"http://m.xiaolu.so/rest/v1/products/18045.json","name":"韩版潮范皮衣夹克/黑色","outer_id":"913244650011","category":{"cid":13,"parent_cid":5,"name":"上装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvADR3DUV0O1x6wcEOmlfrWdqoaprCUbibOdJR8TswsfraVHZjboLW99ySYQkYxYMntQgHAp45W0fg/0?wx_fmt=png","remain_num":3,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451559270223-0.png","is_saleopen":true,"is_newgood":true,"std_sale_price":389,"agent_price":59.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":59.9,"product_lowest_price":59.9,"product_model":{"id":1998,"name":"韩版潮范皮衣夹克","head_imgs":["http://image.xiaolu.so/MG-1451559270223-0.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvWkGj5Jjnuc9KwbWFTR6QOXoOXlSBiaamr7kVPCzGCQ4f99hCLibBO2DUBe4kvicMoib0zQ5fHw4gwuw/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvWkGj5Jjnuc9KwbWFTR6QO23ibHMdGZ1LsfvP0wdbPf1HIb0icfMYqYAtUGToXUBdsVSAvRZg6Glyg/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":1998},{"id":18834,"url":"http://m.xiaolu.so/rest/v1/products/18834.json","name":"小狗纯色连帽棉衣/黑色","outer_id":"912258710011","category":{"cid":12,"parent_cid":5,"name":"外套","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuTXMSnRUGHnQgwX7vAOcdR5esicHHILlE0BHWam0VUaodj4IoiaOSRwD3t1KtL0zj4PXVQBUjDxamA/0?wx_fmt=png","remain_num":3,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451556807076-头图.png","is_saleopen":true,"is_newgood":true,"std_sale_price":399,"agent_price":69.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":69.9,"product_lowest_price":69.9,"product_model":{"id":2316,"name":"小狗纯色连帽棉衣","head_imgs":["http://image.xiaolu.so/MG-1451556807076-头图.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuTXMSnRUGHnQgwX7vAOcdRCQ9X7gDibV49JEscFK98dyclzC9mWpcWPxI8C8MoryDricp6SKR5Npibw/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":2316},{"id":19634,"url":"http://m.xiaolu.so/rest/v1/products/19634.json","name":"韩版可爱兔子长T/绿色","outer_id":"913147470011","category":{"cid":13,"parent_cid":5,"name":"上装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvJffUkcKQzdx4VicMvQAkO455NJE6zTpam50IjXK3QprqpK48ay9Lkjd6PnFK9YG8xgM35zIaLSgg/0?wx_fmt=png","remain_num":7,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451555027291-0.png","is_saleopen":true,"is_newgood":true,"std_sale_price":199,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":2616,"name":"韩版可爱兔子长T","head_imgs":["http://image.xiaolu.so/MG-1451555027291-0.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvJffUkcKQzdx4VicMvQAkO4yFibnMNCicmLwt9a8NX8SRSN1RfCiasXLV5Pts6ibaRIJ4ah5OkJX3TcGg/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvJffUkcKQzdx4VicMvQAkO4A7vIgKmwpe2w4zHU9ZL0aqmlEfKUXPnhOxmE7hHoCbeFn75UXACXBw/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLvJffUkcKQzdx4VicMvQAkO4fg2bWz3dSrt8ytQkornrJuWldYrus8uQLKOqhmtcibVAE1UNmu00n3g/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":2616},{"id":20167,"url":"http://m.xiaolu.so/rest/v1/products/20167.json","name":"韩版潮格毛线衫/红色","outer_id":"913261530011","category":{"cid":13,"parent_cid":5,"name":"上装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuefeZRP7bcXtMMPzia8bjiaIicwvVXxkyfJhyyM8IYUg38rk3npU4u8h9CvBsJ07WGTibNWz6AIQB2jA/0?wx_fmt=png","remain_num":8,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451556619543-0.png","is_saleopen":true,"is_newgood":true,"std_sale_price":345,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":2801,"name":"韩版潮格毛线衫","head_imgs":["http://image.xiaolu.so/MG-1451556619543-0.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuefeZRP7bcXtMMPzia8bjiaIkZPWsy9ChtCtUXRo09dLP3CXTDQv5Kic3M98DvtcA9YWVjNibhv1RSrA/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLuefeZRP7bcXtMMPzia8bjiaIMg8WKgWxibEbsABNgPgzLJ1H36uM3HCemUj4rPQa8DpPd4NLLrDZF8w/0?wx_fmt=png"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":2801},{"id":20808,"url":"http://m.xiaolu.so/rest/v1/products/20808.json","name":"百变潮童时尚翅膀卫衣/黑色","outer_id":"913252570021","category":{"cid":13,"parent_cid":5,"name":"上装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt7YMYhjrHxcMePelabrGhKFczIcxibAU554MYCw0bqjuLbDdBqE7fHemXfibxHX90nz05CQuib7w7uA/0?wx_fmt=png","remain_num":3,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451557575898-0.png","is_saleopen":true,"is_newgood":true,"std_sale_price":249,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":3030,"name":"百变潮童时尚翅膀卫衣","head_imgs":["http://image.xiaolu.so/MG-1451557575898-0.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtmZxmwpvTgK4Lz0OKA7icvHuylyqf8qvLgr8tmxhqN1DwJqoniaO6yibtoanuzeeRLyBzz1Ms9uH4RQ/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":3030},{"id":22569,"url":"http://m.xiaolu.so/rest/v1/products/22569.json","name":"小黄人卡通纯棉外套/黄色","outer_id":"912272910011","category":{"cid":12,"parent_cid":5,"name":"外套","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLv5k9Zv0iceMKxMc0dPVoHZQGLCwXs7QgWKslpMh5qCNusM5d2NdUIwL4c57EfPhZm89an9mibibn40g/0?wx_fmt=png","remain_num":3,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451555694989-头图.png","is_saleopen":true,"is_newgood":true,"std_sale_price":249,"agent_price":39.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":39.9,"product_lowest_price":39.9,"product_model":{"id":3713,"name":"小黄人卡通纯棉外套","head_imgs":["http://image.xiaolu.so/MG-1451555694989-头图.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLv5k9Zv0iceMKxMc0dPVoHZQibP8LO8hc2P3XOd01BOXs0FYBehWNoJox356ovNhR9WM2hgrBQXLmdA/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":3713},{"id":23476,"url":"http://m.xiaolu.so/rest/v1/products/23476.json","name":"韩版米奇连帽外套/红色","outer_id":"912249530081","category":{"cid":12,"parent_cid":5,"name":"外套","status":"normal","sort_order":0},"pic_path":"http://image.xiaolu.so/TT-1445932105933-%E9%9F%A9%E7%89%88%E7%B1%B3%E5%A5%87%E8%BF%9E%E5%B8%BD%E5%A4%96%E5%A5%973.png","remain_num":8,"is_saleout":false,"head_img":"http://image.xiaolu.so/MG-1451559186141-0.png","is_saleopen":true,"is_newgood":true,"std_sale_price":299,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":4038,"name":"韩版米奇连帽外套","head_imgs":["http://image.xiaolu.so/MG-1451559186141-0.png"],"content_imgs":["http://image.xiaolu.so/TT-1445932008884-%E9%9F%A9%E7%89%88%E7%B1%B3%E5%A5%87%E8%BF%9E%E5%B8%BD%E5%A4%96%E5%A5%975.jpg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":4038},{"id":3085,"url":"http://m.xiaolu.so/rest/v1/products/3085.json","name":"秒杀 萌宝字母印花套装-黑色","outer_id":"9030413801","category":{"cid":15,"parent_cid":5,"name":"套装","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt5jD50IzPhoeeiakX9272IwpwdLwnt00VyuHjTJWL2ujyNpWibFkd7mOiaAxRcufjJ1kg4eaP4E7Jpw/0?wx_fmt=jpeg","remain_num":0,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451397181028-秒杀.png","is_saleopen":true,"is_newgood":true,"std_sale_price":179,"agent_price":29.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":29.9,"product_lowest_price":29.9,"product_model":{"id":6813,"name":"秒杀 萌宝字母印花套装","head_imgs":["http://image.xiaolu.so/MG-1451397181028-秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt5jD50IzPhoeeiakX9272IwEjhPOTcGGPMqd3P7BfuDInGlz3Bvb6PnrO45uU8bzd3BDx9uSSlIyw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt5jD50IzPhoeeiakX9272IwcZKFXtMibYtIudJMeDbkYB7SKDiaGDKwveBUyE4VSpUeKUicCvtYoRqfg/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt5jD50IzPhoeeiakX9272Iw4YribQSzNmKeSThK5TQribHR5lnatw954dokRLJ9VUEcXEdoSTW2PNgQ/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLt5jD50IzPhoeeiakX9272IwAJvN7MNQ1ro6fY30ibuaiaV56fUd018KZz4RbGcvdyvZ7OyFdAEicRBCQ/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":1,"is_verify":false,"model_id":6813},{"id":23062,"url":"http://m.xiaolu.so/rest/v1/products/23062.json","name":"韩版蕾丝公主裙/黑白","outer_id":"914155970011","category":{"cid":14,"parent_cid":5,"name":"连衣裙","status":"normal","sort_order":0},"pic_path":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLshZTkGcoSibjq8Bn2OqATo3EcbQ7vCwd2W2sWkoq0VgVn6gCYM7qTciaZE9r8YYfMBtBBX2IOZyAxg/0?wx_fmt=png","remain_num":2,"is_saleout":true,"head_img":"http://image.xiaolu.so/MG-1451554863472-0.png","is_saleopen":true,"is_newgood":true,"std_sale_price":349,"agent_price":49.9,"sale_time":"2016-01-03","offshelf_time":null,"memo":"","lowest_price":49.9,"product_lowest_price":49.9,"product_model":{"id":3892,"name":"韩版蕾丝公主裙","head_imgs":["http://image.xiaolu.so/MG-1451554863472-0.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLshZTkGcoSibjq8Bn2OqATo3RiatxZiboZJAt1pKdhXsQ0oNWKv5j5k4McS658bmaokvKm9SV12YWMcQ/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":false,"buy_limit":false,"per_limit":5},"ware_by":2,"is_verify":false,"model_id":3892}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private Object mNext;
  @SerializedName("previous") private Object mPrevious;
  /**
   * id : 12171
   * url : http://m.xiaolu.so/rest/v1/products/12171.json
   * name : 秒杀 韩版撞色时尚条纹系列套装/系列1
   * outer_id : 80301521301
   * category : {"cid":22,"parent_cid":8,"name":"套装","status":"normal","sort_order":100}
   * pic_path : https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLukME8TkAGP3Z5UNh7ncA5ll5edUFibibiaQhj2aSJEN9KIQO2zqIg8GdJ0fuUA6cj1lLibC6HTfxgByw/0?wx_fmt=png
   * remain_num : 5
   * is_saleout : true
   * head_img : http://image.xiaolu.so/MG-1451396496306-0秒杀.png
   * is_saleopen : true
   * is_newgood : true
   * std_sale_price : 430
   * agent_price : 59.9
   * sale_time : 2016-01-03
   * offshelf_time : null
   * memo :
   * lowest_price : 59.9
   * product_lowest_price : 59.9
   * product_model : {"id":6811,"name":"秒杀 韩版撞色时尚条纹系列套装","head_imgs":["http://image.xiaolu.so/MG-1451396496306-0秒杀.png"],"content_imgs":["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQib9pascibFYl1iaMlc587ox9fqU2J5Jbq1tMbWAQte8PZ8oCLhRw0pYnQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQUtcla1fCLlg82yUAOnibDRW04Dwag0cWibOlq4ZiaiaTmymhuD3IJibibecw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQWIBdubgwcDXyLhtcSOhQhrz6rjogibZrHztQbClVcXKp9L0yetvianIw/0?wx_fmt=jpeg"],"is_single_spec":false,"is_sale_out":true,"buy_limit":false,"per_limit":5}
   * ware_by : 1
   * is_verify : false
   * model_id : 6811
   */

  @SerializedName("results") private List<ResultsEntity> mResults;

  public void setCount(int count) {
    this.mCount = count;
  }

  public void setNext(Object next) {
    this.mNext = next;
  }

  public void setPrevious(Object previous) {
    this.mPrevious = previous;
  }

  public void setResults(List<ResultsEntity> results) {
    this.mResults = results;
  }

  public int getCount() {
    return mCount;
  }

  public Object getNext() {
    return mNext;
  }

  public Object getPrevious() {
    return mPrevious;
  }

  public List<ResultsEntity> getResults() {
    return mResults;
  }

  public static class ResultsEntity {
    @SerializedName("id") private String mId;
    @SerializedName("url") private String mUrl;
    @SerializedName("name") private String mName;
    @SerializedName("outer_id") private String mOuterId;
    /**
     * cid : 22
     * parent_cid : 8
     * name : 套装
     * status : normal
     * sort_order : 100
     */

    @SerializedName("category") private CategoryEntity mCategory;
    @SerializedName("pic_path") private String mPicPath;
    @SerializedName("remain_num") private int mRemainNum;
    @SerializedName("is_saleout") private boolean mIsSaleout;
    @SerializedName("head_img") private String mHeadImg;
    @SerializedName("is_saleopen") private boolean mIsSaleopen;
    @SerializedName("is_newgood") private boolean mIsNewgood;
    @SerializedName("std_sale_price") private double mStdSalePrice;
    @SerializedName("agent_price") private double mAgentPrice;
    @SerializedName("sale_time") private String mSaleTime;
    @SerializedName("offshelf_time") private Object mOffshelfTime;
    @SerializedName("memo") private String mMemo;
    @SerializedName("lowest_price") private double mLowestPrice;
    @SerializedName("product_lowest_price") private double mProductLowestPrice;
    /**
     * id : 6811
     * name : 秒杀 韩版撞色时尚条纹系列套装
     * head_imgs : ["http://image.xiaolu.so/MG-1451396496306-0秒杀.png"]
     * content_imgs : ["https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQib9pascibFYl1iaMlc587ox9fqU2J5Jbq1tMbWAQte8PZ8oCLhRw0pYnQ/0?wx_fmt=png","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQUtcla1fCLlg82yUAOnibDRW04Dwag0cWibOlq4ZiaiaTmymhuD3IJibibecw/0?wx_fmt=jpeg","https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLtiakvDbuLRhgiaaXD4LnAcWQWIBdubgwcDXyLhtcSOhQhrz6rjogibZrHztQbClVcXKp9L0yetvianIw/0?wx_fmt=jpeg"]
     * is_single_spec : false
     * is_sale_out : true
     * buy_limit : false
     * per_limit : 5
     */

    @SerializedName("product_model") private ProductModelEntity mProductModel;
    @SerializedName("ware_by") private int mWareBy;
    @SerializedName("is_verify") private boolean mIsVerify;
    @SerializedName("model_id") private int mModelId;

    public void setId(String id) {
      this.mId = id;
    }

    public void setUrl(String url) {
      this.mUrl = url;
    }

    public void setName(String name) {
      this.mName = name;
    }

    public void setOuterId(String outerId) {
      this.mOuterId = outerId;
    }

    public void setCategory(CategoryEntity category) {
      this.mCategory = category;
    }

    public void setPicPath(String picPath) {
      this.mPicPath = picPath;
    }

    public void setRemainNum(int remainNum) {
      this.mRemainNum = remainNum;
    }

    public void setIsSaleout(boolean isSaleout) {
      this.mIsSaleout = isSaleout;
    }

    public void setHeadImg(String headImg) {
      this.mHeadImg = headImg;
    }

    public void setIsSaleopen(boolean isSaleopen) {
      this.mIsSaleopen = isSaleopen;
    }

    public void setIsNewgood(boolean isNewgood) {
      this.mIsNewgood = isNewgood;
    }

    public void setStdSalePrice(int stdSalePrice) {
      this.mStdSalePrice = stdSalePrice;
    }

    public void setAgentPrice(double agentPrice) {
      this.mAgentPrice = agentPrice;
    }

    public void setSaleTime(String saleTime) {
      this.mSaleTime = saleTime;
    }

    public void setOffshelfTime(Object offshelfTime) {
      this.mOffshelfTime = offshelfTime;
    }

    public void setMemo(String memo) {
      this.mMemo = memo;
    }

    public void setLowestPrice(double lowestPrice) {
      this.mLowestPrice = lowestPrice;
    }

    public void setProductLowestPrice(double productLowestPrice) {
      this.mProductLowestPrice = productLowestPrice;
    }

    public void setProductModel(ProductModelEntity productModel) {
      this.mProductModel = productModel;
    }

    public void setWareBy(int wareBy) {
      this.mWareBy = wareBy;
    }

    public void setIsVerify(boolean isVerify) {
      this.mIsVerify = isVerify;
    }

    public void setModelId(int modelId) {
      this.mModelId = modelId;
    }

    public String getId() {
      return mId;
    }

    public String getUrl() {
      return mUrl;
    }

    public String getName() {
      return mName;
    }

    public String getOuterId() {
      return mOuterId;
    }

    public CategoryEntity getCategory() {
      return mCategory;
    }

    public String getPicPath() {
      return mPicPath;
    }

    public int getRemainNum() {
      return mRemainNum;
    }

    public boolean isIsSaleout() {
      return mIsSaleout;
    }

    public String getHeadImg() {
      return mHeadImg;
    }

    public boolean isIsSaleopen() {
      return mIsSaleopen;
    }

    public boolean isIsNewgood() {
      return mIsNewgood;
    }

    public double getStdSalePrice() {
      return mStdSalePrice;
    }

    public double getAgentPrice() {
      return mAgentPrice;
    }

    public String getSaleTime() {
      return mSaleTime;
    }

    public Object getOffshelfTime() {
      return mOffshelfTime;
    }

    public String getMemo() {
      return mMemo;
    }

    public double getLowestPrice() {
      return mLowestPrice;
    }

    public double getProductLowestPrice() {
      return mProductLowestPrice;
    }

    public ProductModelEntity getProductModel() {
      return mProductModel;
    }

    public int getWareBy() {
      return mWareBy;
    }

    public boolean isIsVerify() {
      return mIsVerify;
    }

    public int getModelId() {
      return mModelId;
    }

    public static class CategoryEntity {
      @SerializedName("cid") private int mCid;
      @SerializedName("parent_cid") private int mParentCid;
      @SerializedName("name") private String mName;
      @SerializedName("status") private String mStatus;
      @SerializedName("sort_order") private int mSortOrder;

      public void setCid(int cid) {
        this.mCid = cid;
      }

      public void setParentCid(int parentCid) {
        this.mParentCid = parentCid;
      }

      public void setName(String name) {
        this.mName = name;
      }

      public void setStatus(String status) {
        this.mStatus = status;
      }

      public void setSortOrder(int sortOrder) {
        this.mSortOrder = sortOrder;
      }

      public int getCid() {
        return mCid;
      }

      public int getParentCid() {
        return mParentCid;
      }

      public String getName() {
        return mName;
      }

      public String getStatus() {
        return mStatus;
      }

      public int getSortOrder() {
        return mSortOrder;
      }
    }

    public static class ProductModelEntity {
      @SerializedName("id") private String mId;
      @SerializedName("name") private String mName;
      @SerializedName("is_single_spec") private boolean mIsSingleSpec;
      @SerializedName("is_sale_out") private boolean mIsSaleOut;
      @SerializedName("buy_limit") private boolean mBuyLimit;
      @SerializedName("per_limit") private int mPerLimit;
      @SerializedName("head_imgs") private List<String> mHeadImgs;
      @SerializedName("content_imgs") private List<String> mContentImgs;

      public void setId(String id) {
        this.mId = id;
      }

      public void setName(String name) {
        this.mName = name;
      }

      public void setIsSingleSpec(boolean isSingleSpec) {
        this.mIsSingleSpec = isSingleSpec;
      }

      public void setIsSaleOut(boolean isSaleOut) {
        this.mIsSaleOut = isSaleOut;
      }

      public void setBuyLimit(boolean buyLimit) {
        this.mBuyLimit = buyLimit;
      }

      public void setPerLimit(int perLimit) {
        this.mPerLimit = perLimit;
      }

      public void setHeadImgs(List<String> headImgs) {
        this.mHeadImgs = headImgs;
      }

      public void setContentImgs(List<String> contentImgs) {
        this.mContentImgs = contentImgs;
      }

      public String getId() {
        return mId;
      }

      public String getName() {
        return mName;
      }

      public boolean isIsSingleSpec() {
        return mIsSingleSpec;
      }

      public boolean isIsSaleOut() {
        return mIsSaleOut;
      }

      public boolean isBuyLimit() {
        return mBuyLimit;
      }

      public int getPerLimit() {
        return mPerLimit;
      }

      public List<String> getHeadImgs() {
        return mHeadImgs;
      }

      public List<String> getContentImgs() {
        return mContentImgs;
      }
    }
  }
}
