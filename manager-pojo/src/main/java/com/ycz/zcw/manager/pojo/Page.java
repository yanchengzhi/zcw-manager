package com.ycz.zcw.manager.pojo;

import java.util.List;

/**
 * �����÷�װ��ҳ��ѯ���
 * @author Administrator
 *
 */
public class Page<T> {
	
	private List<T> datas;//��ѯ���Ľ����
	private int page;//ҳ��
	private int maxPage;//���ҳ��
	private int totalSize;//�ܼ�¼����
	
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

}
