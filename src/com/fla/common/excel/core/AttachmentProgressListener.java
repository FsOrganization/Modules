package com.fla.common.excel.core;

import org.apache.commons.fileupload.ProgressListener;
import javax.servlet.http.HttpSession;

public class AttachmentProgressListener implements ProgressListener {

	private HttpSession session;

	public AttachmentProgressListener(HttpSession session) {
		this.session = session;
	}

	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		double rate = (double) pBytesRead / pContentLength * 80;
		if (rate > 80) {
			rate = 80;
		}
		session.setAttribute("progressRate", rate);
	}
}
