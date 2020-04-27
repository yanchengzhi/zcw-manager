package com.ycz.zcw.manager.service;

import java.util.List;

import com.ycz.zcw.manager.pojo.Tag;

public interface ItemTagService {

    List<Tag> getAll();//��ȡ���нڵ�

    Tag queryTagById(Integer id);//��ID��ѯ��ǩ

    Tag queryTagByName(String name);//�����Ʋ��ұ�ǩ

    void addTag(Tag tag);//��ӱ�ǩ

    List<Tag> queryTwoTags();//��ȡ����һ���˵�

    void editTag(Tag tag);//�޸ı�ǩ

    void deleteTag(Integer id);//ɾ����ǩ

}
