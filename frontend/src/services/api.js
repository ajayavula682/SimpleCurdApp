import axios from 'axios'

const API_BASE = 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_BASE,
  headers: { 'Content-Type': 'application/json' }
})

export const userApi = {
  getAll: () => api.get('/users'),
  getById: (id) => api.get(`/users/${id}`),
  create: (data) => api.post('/users', data),
  update: (id, data) => api.put(`/users/${id}`, data),
  delete: (id) => api.delete(`/users/${id}`),
  search: (keyword) => api.get(`/users/search?keyword=${keyword}`),
  getActive: () => api.get('/users/active'),
  deactivate: (id) => api.patch(`/users/${id}/deactivate`),
  activate: (id) => api.patch(`/users/${id}/activate`)
}

export const productApi = {
  getAll: () => api.get('/products'),
  getById: (id) => api.get(`/products/${id}`),
  create: (data) => api.post('/products', data),
  update: (id, data) => api.put(`/products/${id}`, data),
  delete: (id) => api.delete(`/products/${id}`),
  search: (keyword) => api.get(`/products/search?keyword=${keyword}`),
  getCategories: () => api.get('/products/categories'),
  getByCategory: (category) => api.get(`/products/category/${category}`),
  getAvailable: () => api.get('/products/available'),
  getInStock: () => api.get('/products/in-stock'),
  getByPriceRange: (minPrice, maxPrice) => api.get(`/products/price-range?minPrice=${minPrice}&maxPrice=${maxPrice}`),
  updateAvailability: (id, isAvailable) => api.patch(`/products/${id}/availability?isAvailable=${isAvailable}`),
  updateQuantity: (id, quantity) => api.patch(`/products/${id}/quantity?quantity=${quantity}`)
}
