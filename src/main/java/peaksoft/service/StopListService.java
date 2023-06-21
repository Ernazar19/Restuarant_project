package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.response.pagination.PaginationResponseStopList;
import peaksoft.entity.StopList;

public interface StopListService {
    PaginationResponseStopList getAllStopList(int pageSize, int currentPage);
    public SimpleResponse save(Long menuId, StopListRequest request);
    StopListResponse findById(Long id);
    SimpleResponse update(Long id,StopListRequest stopListRequest);
    SimpleResponse deleteById(Long id);
}
