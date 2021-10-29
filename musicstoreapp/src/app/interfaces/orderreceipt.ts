import { CartToOrder } from '../interfaces/carttoorder';
import { Order } from '../interfaces/order';

export interface OrderReceipt{
  boughtitems: CartToOrder[];
  order : Order;
}
