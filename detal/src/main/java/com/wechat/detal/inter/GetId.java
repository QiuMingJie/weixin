package com.wechat.detal.inter;

import java.io.Serializable;

public interface GetId<K extends Serializable, T>{
	K getId(T t);
}
