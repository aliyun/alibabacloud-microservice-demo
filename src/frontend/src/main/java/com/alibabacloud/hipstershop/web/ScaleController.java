package com.alibabacloud.hipstershop.web;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yizhan.xj
 */

@RestController
@RequestMapping("/scale")
public class ScaleController {

	@RequestMapping(value = "/begin", method = RequestMethod.GET)
	public String index() {

		Random r = new Random(100);
		String str = "fwljfdsklvnxcewewrewrew12wre5rewf1ew2few4few2few2few3few3few5fsd1sdewu3249gdfkvdvx"
				+ "wefsdjfewvmdxlvdsfofewmvdmvfd;lvds;vds;vdsvdsxcnzgewgdfuvxmvx.;f"
				+ "fsaffsdjlvcx.vcxgdfjkf;dsfdas#vdsjlfdsmv.xc.vcxjk;fewipvdmsvzlfsjlf;afdjsl;fdsp[euiprenvs"
				+ "fsdovxc.vmxceworupg;";
		float i = r.nextFloat();
		float j = 232.13243f;
		ArrayList<String> list = new ArrayList<String>();
		for (int x = 0; x < 10; x++) {
			for (int k = 0; k < 10000; k++) {
				j = i * j;
				list.add(str + String.valueOf(j));
			}
		}

		return "success";
	}

}
