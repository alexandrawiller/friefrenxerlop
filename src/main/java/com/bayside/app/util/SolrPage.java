package com.bayside.app.util;

import java.util.List;
import java.util.Map;

public class SolrPage<T> {
	  /** 每页显示条数默认为30条 */
    public static final int DEFAULT_SIZE = 30;

    /** 当前页码， 从1开始计 */
    private int pageNum;

    /** 每页条数 */
    private int pageSize;

    /** 总条数 */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /** 查询参数 */
    private Map<String, Object> conditions;

    /** 当前页数据 */
    private List<T> datas;
  //第一页
    private int firstPage;
    //前一页
    private int prePage;
    //下一页
    private int nextPage;
    //最后一页
    private int lastPage;
  //是否为第一页
    private boolean isFirstPage = false;
    //是否为最后一页
    private boolean isLastPage = false;
    //是否有上一页
    private boolean hasPreviousPage = false;
    //是否有下一页	
    private boolean hasNextPage = false;
    //导航页码数
    private int navigatePages;
    //所有导航页号
    private int[] navigatepageNums;
    public SolrPage() {
        // 设置默认值
    	pageNum = 1;
    	pageSize = DEFAULT_SIZE;
    }
    /** 获取当前页码 */
    public int getPageNum() {
        return pageNum;
    }

    /** 设置当前页码 */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /** 获取每页显示条数 */
    public int getPageSize() {
        return pageSize;
    }

    /** 设置每页显示条数 */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /** 获取查询参数 */
    public Map<String, Object> getConditions() {
        return conditions;
    }

    /** 设置查询参数 */
    public void setConditions(Map<String, Object> conditions) {
        this.conditions = conditions;
    }

    /** 获取当前页数据 */
    public List<T> getDatas() {
        return datas;
    }

    /** 设置当前页数据 */
    public void setDatas(List<T> datas) {
    	if(datas!=null && !datas.isEmpty()){
    	this.pages=(int) getTotalPages();
    	  //当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
        calcPage();
        judgePageBoudary();
    	}
        this.datas = datas;
    }
    private void calcPage() {
        if (navigatepageNums != null && navigatepageNums.length > 0) {
            firstPage = navigatepageNums[0];
            lastPage = navigatepageNums[navigatepageNums.length - 1];
            if (pageNum > 1) {
                prePage = pageNum - 1;
            }
            if (pageNum < pages) {
                nextPage = pageNum + 1;
            }
        }
    }
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }
    /** 获取总条数 */
    public long getTotal() {
        return total;
    }

    /** 设置总条数 */
    public void setTotal(long total) {
    	
        this.total = total;
    }

    public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}


	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}

	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}

	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}

	public boolean getIsFirstPage() {
		return isFirstPage;
	}
	public void setIsFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}
	public boolean getIsLastPage() {
		return isLastPage;
	}
	public void setIsLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	public boolean getHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public boolean getHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public static int getDefaultSize() {
		return DEFAULT_SIZE;
	}

	/** 获取总页数 */
    public long getTotalPages() {
        long totalPages = total / pageSize;
        if (total%pageSize != 0) {
            totalPages ++;
        }
        return totalPages;
    }

    /** 获取从第几条数据开始查询 */
    public long getStart() {
        return (pageNum-1) * pageSize;
    }
    
    /** 判断是否还有前一页 */
    public boolean getHasPrevious() {
        return pageNum == 1 ? false : true;
    }
    
    /** 判断是否还有后一页 */
    public boolean getHasNext() {
        return (getTotalPages()!=0 && getTotalPages()!=pageNum) ? true : false;
    }
}
