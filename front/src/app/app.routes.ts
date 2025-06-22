import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import { AuthComponent } from "./auth/features/auth/auth.component";

export const APP_ROUTES: Routes = [
  { path: 'auth', component: AuthComponent }, 
  {
    path: "home",
    component: HomeComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
  },
  { path: "", redirectTo: "auth", pathMatch: "full" },
];
