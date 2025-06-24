import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { FormsModule } from "@angular/forms";
import { DropdownModule } from "primeng/dropdown";
import { CommonModule } from '@angular/common';
import { TagModule } from 'primeng/tag';
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "app/shared/ui/panel-menu/panel-menu.component";
import { UserAuthService } from "app/auth/data-access/user-auth-service.service";
import { Router } from "@angular/router";

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, FormsModule, DropdownModule, CommonModule, TagModule, PanelMenuComponent, SplitterModule, ToolbarModule],
})
export class ProductListComponent implements OnInit {
  title = "ALTEN SHOP";
  private readonly productsService = inject(ProductsService);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);


  public searchTerm = "";
  public selectedCategory = "";
  public categories: string[] = [];

  constructor(public userAuthService: UserAuthService, private router: Router) {

  }

  ngOnInit() {
    this.productsService.get().subscribe(products => {
      this.categories = [...new Set(products.map(p => p.category))];
    });
  }

  public get filteredProducts(): Product[] {
    return this.products()
      .filter(product =>
        product.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        product.description.toLowerCase().includes(this.searchTerm.toLowerCase())
      )
      .filter(product =>
        !this.selectedCategory || product.category === this.selectedCategory
      );
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  public logout() {
    this.userAuthService.logout();
    alert('Vous êtes déconnecté.');
    this.router.navigate(['/auth']);
  }

  public showAdminOptions(): boolean {
    return this.userAuthService.isAdmin();
  }
}
