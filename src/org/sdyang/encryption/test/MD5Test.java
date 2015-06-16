package org.sdyang.encryption.test;

import org.sdyang.encryption.utils.MD5;

public class MD5Test {

	public static void main(String[] args) {
		MD5 md5 = new MD5();
		String sign = md5.sign("channel=UNIONPAY&input_charset=UTF-8&is_refund=false&out_trade_no=662015042200000031&partner=5032734702028001&retcode=0&retmsg=支付成功&service_version=1.0&sign_type=MD5&time_end=20150422180550&trade_amt=5399&trade_state=0");
		System.out.println("签名："+sign);

	}

}
