import React, { useState, useEffect } from 'react';
import { Button, Table, Tabs, Tag, Card, Descriptions } from 'antd';
import { Column } from '@ant-design/plots';
import axios from 'axios';

const { TabPane } = Tabs;

const JobDetail = ({ appId, onBack }) => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        axios.get(`/api/job/${appId}/details`).then(res => {
            setData(res.data);
            setLoading(false);
        });
    }, [appId]);

    if (loading || !data) return <div>Loading...</div>;

    const { job, stages, tables } = data;

    const shuffleConfig = {
        data: stages,
        xField: 'name',
        yField: 'shuffle_read_bytes',
        color: '#1890ff',
        label: { 
            text: 'shuffle_read_bytes',
            position: 'inside', 
            style: { fill: '#FFFFFF', opacity: 0.6 } 
        },
        xAxis: { label: { autoHide: true, autoRotate: false } },
    };

    const tableColumns = [
        { title: 'Table Name', dataIndex: 'table_name' },
        { title: 'Type', dataIndex: 'type', render: t => <Tag color={t === 'INPUT' ? 'blue' : 'green'}>{t}</Tag> },
        { title: 'Size', dataIndex: 'space_bytes', render: v => (v / 1024 / 1024).toFixed(2) + ' MB' },
        { title: 'Files', dataIndex: 'file_count' },
        { title: 'Format', dataIndex: 'format' },
    ];

    return (
        <div>
            <Button onClick={onBack} style={{ marginBottom: 16 }}>Back to List</Button>
            <Descriptions title="Job Overview" bordered>
                <Descriptions.Item label="App ID">{job.app_id}</Descriptions.Item>
                <Descriptions.Item label="Name">{job.name}</Descriptions.Item>
                <Descriptions.Item label="Duration">{(job.duration / 60000).toFixed(1)} min</Descriptions.Item>
                <Descriptions.Item label="CPU vCore Seconds">{job.cpu_vcore_seconds}</Descriptions.Item>
                <Descriptions.Item label="Memory MB Seconds">{job.memory_mb_seconds}</Descriptions.Item>
                <Descriptions.Item label="Slow Reason"><Tag color="red">{job.slow_reason || 'N/A'}</Tag></Descriptions.Item>
            </Descriptions>
            
            <Tabs defaultActiveKey="1" style={{ marginTop: 20 }}>
                <TabPane tab="Shuffle Analysis" key="1">
                    <Card title="Shuffle Read per Stage">
                        <Column {...shuffleConfig} />
                    </Card>
                </TabPane>
                <TabPane tab="Storage Analysis" key="2">
                    <Table dataSource={tables} columns={tableColumns} rowKey="table_name" />
                </TabPane>
            </Tabs>
        </div>
    );
};

export default JobDetail;
