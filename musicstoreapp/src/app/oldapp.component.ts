<div class  ="text">Navbar : Shopname | Product Categories (Dropdown menu) | All Products | Search | Login(new card overlapping)/Username(dropdown that differs if he is admin, producer or customer [<=Orders & shipment (New card)])</div>
<button (click)="getCategories()"> Categories </button>
<button (click)="getByIdCategory()"> Category </button>
<div>
{{ category.id | json}}
{{ category.name | json}}
{{ category.parent | json}}
</div>

<div class  ="text">Show horizontally scrolling cards for new products</div>
<div class  ="text">Show all products on leftside</div>
<div class  ="text">Show all categories on leftside</div>
<div class  ="text">Show popular producers on leftside</div>
<div class  ="text">Show cart (if not logged as session attr, otherwise fetch from database) on rightside</div>

<div class  ="text">Footer : Col 1 : [Terms & Conditions, Privacy] | Col 2 : [About Us, Contacts] | Col 3 : Share it on Facebook ,Instagram, Mail | Col 4 : GitHub link to the creator</div>
