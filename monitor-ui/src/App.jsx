import React, { useState } from 'react';
import { Layout, Menu } from 'antd';
import ConfigManager from './components/ConfigManager';
import SlowJobList from './components/SlowJobList';
import Dashboard from './components/Dashboard';
import MetricDashboard from './components/MetricDashboard';

const { Header, Content } = Layout;

const App = () => {
    const [currentTab, setCurrentTab] = useState('overview');

    const renderContent = () => {
        switch (currentTab) {
            case 'overview': return <Dashboard />;
            case 'slow-jobs': return <SlowJobList />;
            case 'metric-dashboard': return <MetricDashboard />;
            case 'config': return <ConfigManager />;
            default: return <Dashboard />;
        }
    };

    return (
        <Layout className="layout" style={{ minHeight: '100vh' }}>
            <Header>
                <div className="logo" style={{ float: 'left', color: 'white', fontSize: '20px', fontWeight: 'bold', marginRight: '30px' }}>
                    BigDataOps
                </div>
                <Menu 
                    theme="dark" 
                    mode="horizontal" 
                    defaultSelectedKeys={['overview']} 
                    onClick={e => setCurrentTab(e.key)}
                    items={[
                        { key: 'overview', label: 'Overview' },
                        { key: 'slow-jobs', label: 'Job Analysis' },
                        { key: 'metric-dashboard', label: 'Metric Dashboard' },
                        { key: 'config', label: 'Config' }
                    ]}
                />
            </Header>
            <Content style={{ padding: '0 50px', marginTop: '20px' }}>
                <div className="site-layout-content" style={{ background: '#fff', padding: 24, minHeight: 380 }}>
                    {renderContent()}
                </div>
            </Content>
        </Layout>
    );
};

export default App;
