import React, { useState, useEffect } from 'react';
import { Card, Col, Row, Statistic, Table, Tag } from 'antd';
import axios from 'axios';
import { Pie, Column } from '@ant-design/plots';

const Dashboard = () => {
    const [stats, setStats] = useState(null);
    const [jobs, setJobs] = useState([]);
    const [topTables, setTopTables] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const [statsRes, jobsRes, tablesRes] = await Promise.all([
                axios.get('/api/stats'),
                axios.get('/api/jobs'), // Recent jobs
                axios.get('/api/tables')
            ]);
            setStats(statsRes.data);
            setJobs(jobsRes.data);
            setTopTables(tablesRes.data);
        };
        fetchData();
    }, []);

    if (!stats) return <div>Loading...</div>;

    // Data for Charts
    const jobStatusData = [
        { type: 'Success', value: jobs.filter(j => !j.slow_reason).length },
        { type: 'Slow', value: jobs.filter(j => j.slow_reason).length },
    ];

    const pieConfig = {
        data: jobStatusData,
        angleField: 'value',
        colorField: 'type',
        radius: 0.8,
        label: {
            text: 'value',
            position: 'outside',
        },
        interactions: [{ type: 'element-active' }],
    };

    const columns = [
        { title: 'App ID', dataIndex: 'app_id', key: 'app_id' },
        { title: 'Name', dataIndex: 'name', key: 'name' },
        { title: 'Submit Time', dataIndex: 'submit_time', key: 'submit_time', render: t => new Date(t).toLocaleString() },
        { title: 'Duration (min)', dataIndex: 'duration', key: 'duration', render: v => (v / 60000).toFixed(1) },
        { title: 'Status', dataIndex: 'slow_reason', key: 'status', render: t => t ? <Tag color="red">Slow</Tag> : <Tag color="green">Normal</Tag> },
    ];

    return (
        <div>
            <Row gutter={16}>
                <Col span={8}>
                    <Card>
                        <Statistic title="Total Jobs" value={stats.totalJobs} />
                    </Card>
                </Col>
                <Col span={8}>
                    <Card>
                        <Statistic title="Total Output (GB)" value={(stats.totalOutputBytes / 1024 / 1024 / 1024).toFixed(2)} precision={2} />
                    </Card>
                </Col>
                <Col span={8}>
                    <Card>
                        <Statistic title="Total Shuffle (GB)" value={(stats.totalShuffleBytes / 1024 / 1024 / 1024).toFixed(2)} precision={2} />
                    </Card>
                </Col>
            </Row>

            <Row gutter={16} style={{ marginTop: 24 }}>
                <Col span={12}>
                    <Card title="Job Health Distribution">
                        <Pie {...pieConfig} style={{ height: 300 }} />
                    </Card>
                </Col>
                <Col span={12}>
                    <Card title="Top Tables by Size">
                         <Table 
                            dataSource={topTables} 
                            columns={[
                                { title: 'Table Name', dataIndex: 'table_name' }, 
                                { title: 'Size (MB)', dataIndex: 'total_space', render: v => (v / 1024 / 1024).toFixed(2) }
                            ]} 
                            rowKey="table_name" 
                            pagination={false}
                            size="small"
                        />
                    </Card>
                </Col>
            </Row>

            <Card title="Recent Jobs" style={{ marginTop: 24 }}>
                <Table dataSource={jobs.slice(0, 10)} columns={columns} rowKey="app_id" pagination={false} />
            </Card>
        </div>
    );
};

export default Dashboard;
