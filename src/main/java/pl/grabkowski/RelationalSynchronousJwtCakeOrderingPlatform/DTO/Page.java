package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.BadRequestException;

import java.util.List;

public class Page<T> {
    private long currentPage; // counting starts from number 1
    private long offset;

    private long itemsPerPage;
    private long numberOfPages;
    private long totalNumberOfItems;
    private List<T> listOfItems;

    public Page() {
    }

    public Page(long currentPage, long itemsPerPage) {
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
    }

    public Page(long currentPage, long itemsPerPage, long totalNumberOfItems) {
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalNumberOfItems = totalNumberOfItems;

        if(this.itemsPerPage > 0){
            if(this.totalNumberOfItems%this.itemsPerPage == 0){
                this.numberOfPages = this.totalNumberOfItems/this.itemsPerPage ;
            }
            else{
                this.numberOfPages = this.totalNumberOfItems/this.itemsPerPage + 1;
            }
        }
        else{
            throw new BadRequestException("error.bad_request.items_per_page_lower_than_one");
        }
        if(this.currentPage>this.numberOfPages){
            this.currentPage = this.numberOfPages;
        }
        if(this.currentPage>0){
            this.offset = (this.currentPage - 1) * this.itemsPerPage;
        }
        else{
            this.offset = 0;
        }

    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(long itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public long getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(long numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public long getTotalNumberOfItems() {
        return totalNumberOfItems;
    }

    public void setTotalNumberOfItems(long totalNumberOfItems) {
        this.totalNumberOfItems = totalNumberOfItems;
    }

    public List<T> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(List<T> listOfItems) {
        this.listOfItems = listOfItems;
    }
}
