package itv.com.repository.elastic.search.mapper;


import itv.com.business.entity.Asset;

public interface Mapper<T> {
    Asset map(T hit);
}
