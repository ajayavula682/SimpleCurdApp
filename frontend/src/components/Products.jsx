import { useState, useEffect } from 'react'
import { productApi } from '../services/api'
import ProductModal from './ProductModal'
import Toast from './Toast'

export default function Products() {
  const [products, setProducts] = useState([])
  const [filteredProducts, setFilteredProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editingProduct, setEditingProduct] = useState(null)
  const [categories, setCategories] = useState([])
  const [searchTerm, setSearchTerm] = useState('')
  const [selectedCategory, setSelectedCategory] = useState('')
  const [toast, setToast] = useState(null)

  useEffect(() => {
    loadProducts()
    loadCategories()
  }, [])

  useEffect(() => {
    filterProducts()
  }, [products, searchTerm, selectedCategory])

  async function loadProducts() {
    try {
      setLoading(true)
      const response = await productApi.getAll()
      setProducts(response.data)
    } catch (error) {
      showToast('Failed to load products', 'error')
    } finally {
      setLoading(false)
    }
  }

  async function loadCategories() {
    try {
      const response = await productApi.getCategories()
      setCategories(response.data)
    } catch (error) {
      console.error('Failed to load categories')
    }
  }

  function filterProducts() {
    let result = products

    if (searchTerm) {
      result = result.filter(p =>
        p.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        p.description?.toLowerCase().includes(searchTerm.toLowerCase())
      )
    }

    if (selectedCategory) {
      result = result.filter(p => p.category === selectedCategory)
    }

    setFilteredProducts(result)
  }

  async function handleSave(formData) {
    try {
      if (editingProduct) {
        await productApi.update(editingProduct.id, formData)
        showToast('Product updated successfully')
      } else {
        await productApi.create(formData)
        showToast('Product created successfully')
      }
      setShowModal(false)
      setEditingProduct(null)
      loadProducts()
    } catch (error) {
      showToast(error.response?.data?.message || 'Failed to save product', 'error')
    }
  }

  async function handleDelete(id) {
    if (!window.confirm('Are you sure you want to delete this product?')) return

    try {
      await productApi.delete(id)
      showToast('Product deleted successfully')
      loadProducts()
    } catch (error) {
      showToast('Failed to delete product', 'error')
    }
  }

  function handleEdit(product) {
    setEditingProduct(product)
    setShowModal(true)
  }

  function showToast(message, type = 'success') {
    setToast({ message, type })
    setTimeout(() => setToast(null), 3000)
  }

  return (
    <div>
      <div className="section-header">
        <h2>Product Management</h2>
        <button className="btn btn-primary" onClick={() => {
          setEditingProduct(null)
          setShowModal(true)
        }}>
          + Add Product
        </button>
      </div>

      <div className="filters">
        <input
          type="text"
          placeholder="Search products..."
          className="search-input"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <select
          className="filter-select"
          value={selectedCategory}
          onChange={(e) => setSelectedCategory(e.target.value)}
        >
          <option value="">All Categories</option>
          {categories.map(cat => (
            <option key={cat} value={cat}>{cat}</option>
          ))}
        </select>
      </div>

      <div className="table-container">
        <table className="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Category</th>
              <th>Price</th>
              <th>Quantity</th>
              <th>Available</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr><td colSpan="7" className="loading">Loading...</td></tr>
            ) : filteredProducts.length === 0 ? (
              <tr><td colSpan="7" className="loading">No products found</td></tr>
            ) : (
              filteredProducts.map(product => (
                <tr key={product.id}>
                  <td>{product.id}</td>
                  <td>{product.name}</td>
                  <td>{product.category}</td>
                  <td>${product.price}</td>
                  <td>{product.quantity}</td>
                  <td>{product.isAvailable ? '✓' : '✗'}</td>
                  <td>
                    <div className="actions">
                      <button
                        className="btn btn-warning"
                        onClick={() => handleEdit(product)}
                      >
                        Edit
                      </button>
                      <button
                        className="btn btn-danger"
                        onClick={() => handleDelete(product.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <ProductModal
          product={editingProduct}
          onClose={() => setShowModal(false)}
          onSave={handleSave}
        />
      )}

      {toast && <Toast message={toast.message} type={toast.type} />}
    </div>
  )
}
