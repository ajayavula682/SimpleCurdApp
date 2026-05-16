import './App.css'
import { useState } from 'react'
import Products from './components/Products'
import Users from './components/Users'

function App() {
  const [activeTab, setActiveTab] = useState('products')

  return (
    <>
      <header className="header">
        <div className="container">
          <h1>📦 CRUD Management Dashboard</h1>
          <p className="subtitle">Product & User Management System</p>
        </div>
      </header>

      <nav className="nav-tabs">
        <div className="container">
          <button
            className={`tab-btn ${activeTab === 'products' ? 'active' : ''}`}
            onClick={() => setActiveTab('products')}
          >
            Products
          </button>
          <button
            className={`tab-btn ${activeTab === 'users' ? 'active' : ''}`}
            onClick={() => setActiveTab('users')}
          >
            Users
          </button>
        </div>
      </nav>

      <main className="container">
        {activeTab === 'products' && <Products />}
        {activeTab === 'users' && <Users />}
      </main>
    </>
  )
}

export default App
